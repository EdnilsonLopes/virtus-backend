package com.virtus.service;

import com.virtus.domain.dto.EnumDTO;
import com.virtus.domain.dto.response.*;
import com.virtus.domain.entity.*;
import com.virtus.domain.enums.AverageType;
import com.virtus.domain.model.CurrentUser;
import com.virtus.persistence.CycleEntityRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DistributeActivitiesService {

    private final CycleEntityRepository cycleEntityRepository;

    public DistributeActivitiesService(CycleEntityRepository cycleEntityRepository) {
        this.cycleEntityRepository = cycleEntityRepository;
    }

    public List<DistributeActivitiesTreeResponseDTO> findDistributeActivitiesTree(CurrentUser currentUser, Integer entityId) {
        List<CycleEntity> cycleEntities = cycleEntityRepository.findByEntityId(entityId);

        return groupCyclesByEntity(cycleEntities);
    }

    public List<DistributeActivitiesTreeResponseDTO> groupCyclesByEntity(List<CycleEntity> data) {
        Map<Integer, DistributeActivitiesTreeResponseDTO> entityMap = new HashMap<>();

        for (CycleEntity item : data) {
            Integer entityId = item.getEntity() != null ? item.getEntity().getId() : null;
            Cycle cycle = item.getCycle();
            if (entityId != null) {
                if (!entityMap.containsKey(entityId)) {
                    entityMap.put(entityId,
                            new DistributeActivitiesTreeResponseDTO(parseToEntityResponseDTO(
                                    item.getEntity(),
                                    true),
                                    new ArrayList<>()));
                }
                entityMap.get(entityId).getCycles().add(parseToCycleResponse(cycle));
            }
        }

        return new ArrayList<>(entityMap.values());
    }

    protected EntityVirtusResponseDTO parseToEntityResponseDTO(EntityVirtus entity, boolean detailed) {
        if (entity == null) {
            return null;
        }
        EntityVirtusResponseDTO response = new EntityVirtusResponseDTO();
        response.setId(entity.getId());
        response.setDescription(entity.getDescription());
        response.setUf(entity.getUf());
        response.setName(entity.getName());
        response.setCity(entity.getCity());
        response.setAcronym(entity.getAcronym());
        response.setCode(entity.getCode());
        response.setEsi(entity.getEsi());
        response.setSituation(entity.getSituation());
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        if (detailed) {
            response.setCyclesEntity(parseToCyclesEntityResponse(entity.getCycleEntities()));
            response.setPlans(parseToPlansResponse(entity.getPlans()));
        }
        return response;
    }

    private List<PlanResponseDTO> parseToPlansResponse(List<Plan> plans) {
        if (CollectionUtils.isEmpty(plans)) {
            return new ArrayList<>();
        }
        return plans.stream().map(this::parseToPlanResponse).collect(Collectors.toList());
    }

    private PlanResponseDTO parseToPlanResponse(Plan plan) {
        PlanResponseDTO response = new PlanResponseDTO();
        response.setId(plan.getId());
        response.setCreatedAt(plan.getCreatedAt());
        response.setUpdatedAt(plan.getUpdatedAt());
        response.setAuthor(parseToUserResponseDTO(plan.getAuthor()));
        response.setCnpb(plan.getCnpb());
        response.setReference(plan.getReference());
        response.setGuaranteeResource(plan.getGuaranteeResource());
        response.setLegislation(plan.getLegislation());
        response.setModality(plan.getModality());
        return response;
    }

    private List<CycleEntityResponseDTO> parseToCyclesEntityResponse(List<CycleEntity> cycles) {
        if (CollectionUtils.isEmpty(cycles)) {
            return new ArrayList<>();
        }
        return cycles.stream().map(this::parseToCycleEntityResponse).collect(Collectors.toList());
    }

    private CycleEntityResponseDTO parseToCycleEntityResponse(CycleEntity cycleEntity) {
        CycleEntityResponseDTO response = new CycleEntityResponseDTO();
        response.setId(cycleEntity.getId());
        response.setCycle(parseToCycleResponse(cycleEntity.getCycle()));
        response.setEntity(parseToEntityResponseDTO(cycleEntity.getEntity(), false));
        response.setAverageType(parseToAverageTypeEnumResponseDTO(cycleEntity.getAverageType()));
        response.setSupervisor(parseToUserResponseDTO(cycleEntity.getSupervisor()));
        response.setAuthor(parseToUserResponseDTO(cycleEntity.getAuthor()));
        response.setEndsAt(cycleEntity.getEndsAt());
        response.setStartsAt(cycleEntity.getStartsAt());
        return response;
    }

    private CycleResponseDTO parseToCycleResponse(Cycle cycle) {
        CycleResponseDTO response = new CycleResponseDTO();
        response.setId(cycle.getId());
        response.setName(cycle.getName());
        response.setReference(cycle.getReference());
        response.setOrdination(cycle.getOrdination());
        response.setDescription(cycle.getDescription());
        response.setCyclePillars(parseToCyclePillars(cycle.getPillarCycles()));
        return response;
    }

    private List<CyclePillarResponseDTO> parseToCyclePillars(List<PillarCycle> pillarCycles) {
        if (CollectionUtils.isEmpty(pillarCycles)) {
            return new ArrayList<>();
        }
        return pillarCycles.stream().map(this::parseToCyclePillarResponse).collect(Collectors.toList());
    }

    private CyclePillarResponseDTO parseToCyclePillarResponse(PillarCycle pillarCycle) {
        CyclePillarResponseDTO responseDTO = new CyclePillarResponseDTO();
        responseDTO.setId(pillarCycle.getId());
        responseDTO.setAverageType(parseToAverageTypeEnumResponseDTO(pillarCycle.getAverageType()));
        responseDTO.setPillar(parseToPillarResponse(pillarCycle.getPillar()));
        return responseDTO;
    }

    private PillarResponseDTO parseToPillarResponse(Pillar pillar) {
        PillarResponseDTO response = new PillarResponseDTO();
        response.setId(pillar.getId());
        response.setName(pillar.getName());
        response.setOrdination(pillar.getOrdination());
        response.setDescription(pillar.getDescription());
        response.setReference(pillar.getReference());
        response.setAuthor(parseToUserResponseDTO(pillar.getAuthor()));
        response.setCreatedAt(pillar.getCreatedAt());
        response.setUpdatedAt(pillar.getUpdatedAt());
        response.setComponents(parseToPillarComponentsResponse(pillar.getComponentPillars()));
        return response;
    }

    private List<PillarComponentResponseDTO> parseToPillarComponentsResponse(List<ComponentPillar> componentPillars) {
        if (CollectionUtils.isEmpty(componentPillars)) {
            return new ArrayList<>();
        }
        return componentPillars.stream().map(this::parseToPillarComponentResponse).collect(Collectors.toList());
    }

    private PillarComponentResponseDTO parseToPillarComponentResponse(ComponentPillar componentPillar) {
        PillarComponentResponseDTO response = new PillarComponentResponseDTO();
        response.setId(componentPillar.getId());
        response.setAverageType(parseToAverageTypeEnumResponseDTO(componentPillar.getAverageType()));
        response.setAuthor(parseToUserResponseDTO(componentPillar.getAuthor()));
        response.setUpdatedAt(componentPillar.getUpdatedAt());
        response.setCreatedAt(componentPillar.getCreatedAt());
        response.setProbeFile(componentPillar.getProbeFile());
        response.setStandardWeight(componentPillar.getStandardWeight());
        response.setComponent(parseToComponentResponse(componentPillar.getComponent()));
        return response;
    }

    protected ComponentResponseDTO parseToComponentResponse(Component entity) {
        ComponentResponseDTO response = new ComponentResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setOrdination(entity.getOrdination());
        response.setPga(entity.getPga());
        response.setDescription(entity.getDescription());
        response.setReference(entity.getReference());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    protected UserResponseDTO parseToUserResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setRole(parseToRoleResponse(user.getRole()));
        return dto;
    }

    private RoleResponseDTO parseToRoleResponse(Role role) {
        RoleResponseDTO response = new RoleResponseDTO();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDescription(role.getDescription());
        return response;
    }

    protected EnumDTO<Enum> parseToAverageTypeEnumResponseDTO(AverageType averageType) {
        if (averageType == null) {
            return EnumDTO.builder().build();
        }
        return EnumDTO.builder()
                .value(averageType)
                .description(Translator.translate(averageType.getKey()))
                .build();
    }
}
