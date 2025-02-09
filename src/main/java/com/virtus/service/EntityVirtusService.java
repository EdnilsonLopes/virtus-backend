package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.CycleEntityRequestDTO;
import com.virtus.domain.dto.request.EntityVirtusRequestDTO;
import com.virtus.domain.dto.request.PlanRequestDTO;
import com.virtus.domain.dto.request.StartCycleRequestDTO;
import com.virtus.domain.dto.response.*;
import com.virtus.domain.entity.Cycle;
import com.virtus.domain.entity.CycleEntity;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.entity.Plan;
import com.virtus.domain.model.CurrentUser;
import com.virtus.persistence.*;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntityVirtusService extends BaseService<EntityVirtus, EntityVirtusRepository, EntityVirtusRequestDTO, EntityVirtusResponseDTO> {

    private final CycleRepository cycleRepository;
    private final CycleEntityRepository cycleEntityRepository;
    private final PlanRepository planRepository;

    private final CycleService cycleService;

    public EntityVirtusService(EntityVirtusRepository repository,
                               UserRepository userRepository,
                               EntityManagerFactory entityManagerFactory,
                               CycleRepository cycleRepository,
                               CycleEntityRepository cycleEntityRepository,
                               PlanRepository planRepository, CycleService cycleService) {
        super(repository, userRepository, entityManagerFactory);
        this.cycleRepository = cycleRepository;
        this.cycleEntityRepository = cycleEntityRepository;
        this.planRepository = planRepository;
        this.cycleService = cycleService;
    }

    @Override
    protected void afterCreate(EntityVirtus entity) {
        if (!CollectionUtils.isEmpty(entity.getCycleEntities())) {
            cycleService.startCycle(getLoggedUser() != null
                    ? CurrentUser.builder().id(getLoggedUser().getId()).build()
                    : null, entity.getCycleEntities());
        }
    }

    @Override
    protected void afterUpdate(EntityVirtus entity) {
        if (!CollectionUtils.isEmpty(entity.getCycleEntities())) {
            cycleService.startCycle(getLoggedUser() != null
                    ? CurrentUser.builder().id(getLoggedUser().getId()).build()
                    : null, entity.getCycleEntities());
        }
    }

    @Override
    protected EntityVirtusResponseDTO parseToResponseDTO(EntityVirtus entity, boolean detailed) {
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
        response.setCyclesEntity(parseToCyclesEntityResponse(entity.getCycleEntities()));
        if (detailed) {
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
        response.setName(plan.getName());
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
        //response.setEntity(parseToResponseDTO(cycleEntity.getEntity(), false));
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
        return response;
    }

    @Override
    protected EntityVirtus parseToEntity(EntityVirtusRequestDTO body) {
        EntityVirtus entity = body.getId() != null ? getRepository().findById(body.getId()).orElse(new EntityVirtus()) : new EntityVirtus();
        entity.setDescription(body.getDescription());
        entity.setUf(body.getUf());
        entity.setName(body.getName());
        entity.setCity(body.getCity());
        entity.setAcronym(body.getAcronym());
        entity.setCode(body.getCode());
        entity.setEsi(body.getEsi());
        entity.setSituation(body.getSituation());
        entity.setCycleEntities(parseToCyclesEntity(body.getCyclesEntity(), entity));
        entity.setPlans(parseToPlans(body.getPlans(), entity));
        return entity;
    }

    private List<Plan> parseToPlans(List<PlanRequestDTO> plans, EntityVirtus entity) {
        if (CollectionUtils.isEmpty(plans)) {
            return new ArrayList<>();
        }
        return plans.stream().map(request -> parseToPlan(request, entity)).collect(Collectors.toList());
    }

    private Plan parseToPlan(PlanRequestDTO request, EntityVirtus entity) {
        Plan plan;
        if (!CollectionUtils.isEmpty(entity.getPlans())) {
            plan = entity.getPlans().stream().filter(plan1 -> plan1.getId().equals(request.getId())).findFirst().orElse(new Plan());
        } else {
            plan = new Plan();
        }
        plan.setCnpb(request.getCnpb());
        plan.setLegislation(request.getLegislation());
        plan.setModality(request.getModality());
        plan.setEntity(entity);
        plan.setGuaranteeResource(request.getGuaranteeResource());
        plan.setSituation(request.getSituation());
        plan.setName(request.getName());
        plan.setDescription(request.getDescription());
        return plan;
    }

    private List<CycleEntity> parseToCyclesEntity(List<CycleEntityRequestDTO> cyclesEntity, EntityVirtus entity) {
        if (CollectionUtils.isEmpty(cyclesEntity)) {
            return new ArrayList<>();
        }
        return cyclesEntity.stream().map(cycle -> parseToCycleEntity(cycle, entity)).collect(Collectors.toList());
    }

    private CycleEntity parseToCycleEntity(CycleEntityRequestDTO cycle, EntityVirtus entity) {
        CycleEntity cycleEntity;
        if (!CollectionUtils.isEmpty(entity.getCycleEntities())) {
            cycleEntity = entity.getCycleEntities().stream().filter(c -> c.getId().equals(cycle.getId())).findFirst().orElse(new CycleEntity());
        } else {
            cycleEntity = new CycleEntity();
        }
        cycleEntity.setId(cycle.getId());
        cycleEntity.setEntity(entity);
        cycleEntity.setStartsAt(cycle.getStartsAt());
        cycleEntity.setEndsAt(cycle.getEndsAt());
        if (cycle.getSupervisor() != null) {
            cycleEntity.setSupervisor(getUserRepository().findById(cycle.getSupervisor().getId()).orElse(null));
        }
        if (cycle.getAverageType() != null) {
            cycleEntity.setAverageType(cycle.getAverageType().getValue());
        }
        if (cycle.getCycle() != null) {
            cycleEntity.setCycle(cycleRepository.findById(cycle.getCycle().getId()).orElse(null));
        }
        return cycleEntity;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("entity.not.found");
    }

    public List<CycleEntityResponseDTO> findCyclesEntitiesByEntityId(CurrentUser currentUser, Integer entityId) {
        List<CycleEntity> result = cycleEntityRepository.findByEntityId(entityId);
        if (CollectionUtils.isEmpty(result)) {
            return new ArrayList<>();
        }
        return result.stream().map(this::parseToCycleEntityResponse).collect(Collectors.toList());
    }

}
