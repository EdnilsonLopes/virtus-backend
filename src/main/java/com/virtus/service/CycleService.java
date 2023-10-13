package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.CyclePillarRequestDTO;
import com.virtus.domain.dto.request.CycleRequestDTO;
import com.virtus.domain.dto.request.StartCycleRequestDTO;
import com.virtus.domain.dto.response.CyclePillarResponseDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.entity.*;
import com.virtus.domain.model.CurrentUser;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.CycleEntityRepository;
import com.virtus.persistence.CycleRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CycleService extends BaseService<Cycle, CycleRepository, CycleRequestDTO, CycleResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final CycleEntityRepository cycleEntityRepository;

    public CycleService(CycleRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory,
                        CycleEntityRepository cycleEntityRepository) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
        this.cycleEntityRepository = cycleEntityRepository;
    }

    @Override
    protected CycleResponseDTO parseToResponseDTO(Cycle entity, boolean detailed) {
        CycleResponseDTO response = new CycleResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setReference(entity.getReference());
        response.setOrdination(entity.getOrdination());
        response.setDescription(entity.getDescription());
        if (detailed)
            response.setCyclePillars(parseToCyclePillarsResponse(entity.getPillarCycles()));
        return response;
    }

    private List<CyclePillarResponseDTO> parseToCyclePillarsResponse(List<PillarCycle> pillarCycles) {
        if (CollectionUtils.isEmpty(pillarCycles)) {
            return new ArrayList<>();
        }
        return pillarCycles.stream().map(this::parseToCyclePillarResponse).collect(Collectors.toList());
    }

    private CyclePillarResponseDTO parseToCyclePillarResponse(PillarCycle pillarCycle) {
        CyclePillarResponseDTO response = new CyclePillarResponseDTO();
        response.setId(pillarCycle.getId());
        response.setAverageType(parseToAverageTypeEnumResponseDTO(pillarCycle.getAverageType()));
        response.setStandardWeight(pillarCycle.getStandardWeight());
        response.setPillar(parseToPillarResponseDTO(pillarCycle.getPillar(), false));
        response.setCreatedAt(pillarCycle.getCreatedAt());
        response.setUpdatedAt(pillarCycle.getUpdatedAt());
        response.setAuthor(parseToUserResponseDTO(pillarCycle.getAuthor()));
        return response;
    }

    @Override
    protected Cycle parseToEntity(CycleRequestDTO body) {
        Cycle cycle = new Cycle();
        cycle.setId(body.getId());
        cycle.setName(body.getName());
        cycle.setReference(body.getReference());
        cycle.setDescription(body.getDescription());
        cycle.setPillarCycles(parseToCyclePillars(body.getCyclePillars(), cycle));
        return cycle;
    }

    private List<PillarCycle> parseToCyclePillars(List<CyclePillarRequestDTO> cyclePillars, Cycle cycle) {
        if (CollectionUtils.isEmpty(cyclePillars)) {
            return new ArrayList<>();
        }
        return cyclePillars.stream().map(dto -> parseToCyclePillar(dto, cycle)).collect(Collectors.toList());
    }

    private PillarCycle parseToCyclePillar(CyclePillarRequestDTO dto, Cycle cycle) {
        PillarCycle pillarCycle = new PillarCycle();
        pillarCycle.setId(dto.getId());
        pillarCycle.setAuthor(getLoggedUser());
        pillarCycle.setAverageType(dto.getAverageType().getValue());
        pillarCycle.setStandardWeight(dto.getStandardWeight());
        pillarCycle.setPillar(new Pillar(dto.getPillar().getId()));
        pillarCycle.setCycle(cycle);
        return pillarCycle;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("cycle.not.found");
    }

    public void startCycle(CurrentUser currentUser, StartCycleRequestDTO body) {
        User user = null;
        if (currentUser != null && currentUser.getId() != null) {
            user = findUserById(currentUser.getId());
        }
        setLoggedUser(user);
        Cycle cycle = getRepository().findById(body.getCycle().getId()).orElseThrow(() -> new VirtusException(Translator.translate("cycle.not.found")));
        cycle.setName(body.getCycle().getName());
        cycle.setDescription(body.getCycle().getDescription());
        cycle.setUpdatedAt(LocalDateTime.now());

        List<CycleEntity> cycleEntities = body.getEntities()
                .stream()
                .map(entityVirtus -> parseToCycleEntity(entityVirtus, cycle, body.getStartsAt(), body.getEndsAt()))
                .collect(Collectors.toList());

        cycleEntityRepository.saveAll(cycleEntities);
        getRepository().save(cycle);
    }

    private CycleEntity parseToCycleEntity(EntityVirtus entityVirtus, Cycle cycle, LocalDate startsAt, LocalDate endsAt) {
        CycleEntity cycleEntity = new CycleEntity();
        cycleEntity.setStartsAt(startsAt);
        cycleEntity.setEndsAt(endsAt);
        cycleEntity.setEntity(entityVirtus);
        cycleEntity.setCycle(cycle);
        cycleEntity.setCreatedAt(LocalDateTime.now());
        cycleEntity.setUpdatedAt(LocalDateTime.now());
        cycleEntity.setAuthor(getLoggedUser());
        return cycleEntity;
    }
}
