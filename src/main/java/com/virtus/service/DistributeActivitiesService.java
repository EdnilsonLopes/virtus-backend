package com.virtus.service;

import com.virtus.domain.dto.AuditorDTO;
import com.virtus.domain.dto.EnumDTO;
import com.virtus.domain.dto.request.ActivitiesByProductComponentRequestDto;
import com.virtus.domain.dto.response.*;
import com.virtus.domain.entity.*;
import com.virtus.domain.enums.AverageType;
import com.virtus.domain.model.CurrentUser;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.*;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistributeActivitiesService {

    private final CycleEntityRepository cycleEntityRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final JurisdictionRepository jurisdictionRepository;
    private final ProductComponentRepository productComponentRepository;
    private final UserRepository userRepository;

    public DistributeActivitiesService(CycleEntityRepository cycleEntityRepository,
                                       TeamMemberRepository teamMemberRepository,
                                       JurisdictionRepository jurisdictionRepository,
                                       ProductComponentRepository productComponentRepository,
                                       UserRepository userRepository) {
        this.cycleEntityRepository = cycleEntityRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.jurisdictionRepository = jurisdictionRepository;
        this.productComponentRepository = productComponentRepository;
        this.userRepository = userRepository;
    }

    public DistributeActivitiesTreeResponseDTO findDistributeActivitiesTree(CurrentUser currentUser, Integer entityId, Integer cycleId) {
        CycleEntity cycleEntity = cycleEntityRepository.findByEntityIdAndCycleId(entityId, cycleId).orElse(null);
        if (cycleEntity == null) {
            return null;
        }
        DistributeActivitiesTreeResponseDTO response = new DistributeActivitiesTreeResponseDTO();
        List<ProductComponent> productComponents = productComponentRepository
                .findByEntityIdAndCycleId(entityId, cycleId).orElse(new ArrayList<>());

        if (CollectionUtils.isEmpty(productComponents)) {
            throw new VirtusException("Não existem Produtos Componente, tente iniciar o ciclo novamente.");
        }
        List<TeamMember> auditors = teamMemberRepository.findAuditorsByEntityIdAndCycleId(entityId, cycleId);
        List<Jurisdiction> jurisdictions = jurisdictionRepository.findByEntityId(entityId);

        response.setAuditors(parseToAuditorsResponse(auditors));
        response.getAuditors().add(parseAuditor(cycleEntity.getSupervisor()));
        if (!CollectionUtils.isEmpty(jurisdictions)) {
            User boss = jurisdictions.get(0).getOffice().getBoss();
            if (response.getAuditors().stream().filter(auditorDTO -> auditorDTO.getUserId().equals(boss.getId())).findAny().isEmpty()) {
                response.getAuditors().add(parseAuditor(boss));
            }
        }

        response.setProducts(productComponents.stream().map(p -> parseToProductComponentResponse(p, cycleEntity)).collect(Collectors.toList()));
        return response;
    }

    private ProductComponentResponseDTO parseToProductComponentResponse(ProductComponent productComponent, CycleEntity cycleEntity) {
        ProductComponentResponseDTO dto = new ProductComponentResponseDTO();
        dto.setId(productComponent.getId());
        dto.setComponent(parseToComponentResponse(productComponent.getComponent()));
        dto.setPillar(parseToPillarResponse(productComponent.getPillar()));
        dto.setCycle(parseToCycleEntityResponse(cycleEntity));
        dto.setEntity(parseToEntityResponseDTO(productComponent.getEntity(), false));
        if (productComponent.getAuditor() != null)
            dto.setAuditor(AuditorDTO.builder()
                    .userId(productComponent.getAuditor().getId())
                    .name(productComponent.getAuditor().getName())
                    .build());
        if (productComponent.getSupervisor() != null) {
            dto.setSupervisor(SupervisorResponseDTO
                    .builder()
                    .userId(productComponent.getSupervisor().getId())
                    .name(productComponent.getSupervisor().getName())
                    .role(productComponent.getSupervisor().getRole().getName())
                    .build());
        } else {
            dto.setSupervisor(SupervisorResponseDTO
                    .builder()
                    .userId(cycleEntity.getSupervisor().getId())
                    .name(cycleEntity.getSupervisor().getName())
                    .role(cycleEntity.getSupervisor().getRole().getName())
                    .build());
        }
        dto.setStartsAt(productComponent.getStartsAt() != null ? productComponent.getStartsAt() : cycleEntity.getStartsAt());
        dto.setEndsAt(productComponent.getEndsAt() != null ? productComponent.getEndsAt() : cycleEntity.getEndsAt());
        return dto;
    }

    private List<AuditorDTO> parseToAuditorsResponse(List<TeamMember> auditors) {
        if (CollectionUtils.isEmpty(auditors)) {
            return new ArrayList<>();
        }
        return auditors.stream().map(teamMember -> parseAuditor(teamMember.getUser())).collect(Collectors.toList());
    }

    private AuditorDTO parseAuditor(User user) {
        return AuditorDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();
    }

    private TeamMemberDTO parseToTeamMemberResponse(TeamMember teamMember) {
        TeamMemberDTO dto = new TeamMemberDTO();

        dto.setMember(parseToMemberResponseDTO(teamMember.getUser()));
        dto.setStartsAt(teamMember.getStartsAt());
        dto.setEndsAt(teamMember.getEndsAt());

        return dto;
    }

    private MemberResponseDTO parseToMemberResponseDTO(User user) {
        MemberResponseDTO responseDTO = new MemberResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUser(parseToUserResponseDTO(user));
        return responseDTO;
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

    public void distributeActivities(CurrentUser currentUser, List<ActivitiesByProductComponentRequestDto> body) {
        List<ProductComponent> update = new ArrayList<>();
        for (ActivitiesByProductComponentRequestDto activity : body) {
            ProductComponent productComponent = productComponentRepository.findById(activity.getProductComponentId()).orElseThrow(() -> new VirtusException("Produto componente não encontrado!"));
            productComponent.setSupervisor(userRepository.findById(activity.getSupervisorId()).orElse(null));
            productComponent.setAuditor(userRepository.findById(activity.getAuditorId()).orElse(null));
            productComponent.setEndsAt(activity.getEndsAt());
            productComponent.setStartsAt(activity.getStartsAt());
            update.add(productComponent);
        }
        if (!CollectionUtils.isEmpty(update)) {
            productComponentRepository.saveAllAndFlush(update);
        }
    }
}
