package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.CyclePillarRequestDTO;
import com.virtus.domain.dto.request.CycleRequestDTO;
import com.virtus.domain.dto.request.StartCycleRequestDTO;
import com.virtus.domain.dto.response.CyclePillarResponseDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.entity.*;
import com.virtus.domain.model.CurrentUser;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.*;
import com.virtus.translate.Translator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
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
    private final ProductCycleRepository productCycleRepository;
    private final ProductPillarRepository productPillarRepository;
    private final PillarCycleRepository pillarCycleRepository;
    private final ProductComponentRepository productComponentRepository;

    @PersistenceContext
    private EntityManager entityManager;
    private final EntityVirtusRepository entityVirtusRepository;

    public CycleService(CycleRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory,
                        CycleEntityRepository cycleEntityRepository,
                        ProductCycleRepository productCycleRepository,
                        ProductPillarRepository productPillarRepository,
                        PillarCycleRepository pillarCycleRepository,
                        ProductComponentRepository productComponentRepository,
                        EntityVirtusRepository entityVirtusRepository) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
        this.cycleEntityRepository = cycleEntityRepository;
        this.productCycleRepository = productCycleRepository;
        this.productPillarRepository = productPillarRepository;
        this.pillarCycleRepository = pillarCycleRepository;
        this.productComponentRepository = productComponentRepository;
        this.entityVirtusRepository = entityVirtusRepository;
    }

    @Override
    protected CycleResponseDTO parseToResponseDTO(Cycle entity, boolean detailed) {
        CycleResponseDTO response = new CycleResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setReference(entity.getReference());
        response.setOrdination(entity.getOrdination());
        response.setDescription(entity.getDescription());
        if (detailed) {
            response.setCyclePillars(parseToCyclePillarsResponse(entity.getPillarCycles()));
            response.setEntities(parseToEntitiesResponse(entity.getCycleEntities()));
        }
        return response;
    }

    private List<EntityVirtusResponseDTO> parseToEntitiesResponse(List<CycleEntity> cycleEntities) {
        if (CollectionUtils.isEmpty(cycleEntities)) {
            return new ArrayList<>();
        }
        return cycleEntities.stream().map(ce -> parseToEntityVirtusResponse(ce.getEntity())).collect(Collectors.toList());
    }

    private EntityVirtusResponseDTO parseToEntityVirtusResponse(EntityVirtus entity) {
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
        Cycle cycle = body.getId() != null ? getRepository().findById(body.getId()).orElse(new Cycle()) : new Cycle();
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

    @Transactional
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
        final User current = user;
        removeProductCycles(cycle);
        removeProductPillar(cycle);
        removeProductComponent(cycle);
        cycleEntities.forEach(cycleEntity -> {
            createProductCycle(current, cycleEntity.getEntity(), cycle);
            createProductPillar(current, cycleEntity.getEntity(), cycle);
            createProductComponent(current, cycleEntity.getEntity(), cycle);
        });
        getRepository().save(cycle);
    }

    private void removeProductCycles(Cycle cycle) {
        if (!CollectionUtils.isEmpty(cycle.getProductsCycles())) {
            productCycleRepository.deleteAllByIdInBatch(cycle.getProductsCycles().stream().map(p -> p.getId()).collect(Collectors.toList()));
        }
    }

    private void removeProductPillar(Cycle cycle) {
        if (!CollectionUtils.isEmpty(cycle.getProductsPillars())) {
            productPillarRepository.deleteAllByIdInBatch(cycle.getProductsPillars().stream().map(p -> p.getId()).collect(Collectors.toList()));
        }
    }

    private void removeProductComponent(Cycle cycle) {
        if (!CollectionUtils.isEmpty(cycle.getProductsComponents())) {
            productComponentRepository.deleteAllByIdInBatch(cycle.getProductsComponents().stream().map(p -> p.getId()).collect(Collectors.toList()));
        }
    }

    private ProductCycle createProductCycle(User user, EntityVirtus entity, Cycle cycle) {
        ProductCycle productCycle = new ProductCycle();
        productCycle.setEntity(entity);
        productCycle.setCycle(cycle);
        productCycle.setGrade(BigDecimal.ZERO);
        productCycle.setAuthor(user);
        return productCycleRepository.save(productCycle);
    }

    private int createProductComponent(User current, EntityVirtus entity, Cycle cycle) {
        String jpql =
                "INSERT INTO virtus.produtos_componentes (id_entidade, id_ciclo, id_pilar, id_componente, peso, nota, id_tipo_pontuacao, id_author, criado_em) " +
                        "SELECT :entidadeId, :cicloId, a.id_pilar, b.id_componente, " +
                        "CASE WHEN c.peso_padrao <> 0 THEN ROUND(AVG(c.peso_padrao), 2) ELSE 1 END, 0, :gradeTypeId, :currentUser, :date " +
                        "FROM virtus.pilares_ciclos a " +
                        "LEFT JOIN virtus.componentes_pilares b ON a.id_pilar = b.id_pilar " +
                        "LEFT JOIN virtus.elementos_componentes c ON b.id_componente = c.id_componente " +
                        "WHERE a.id_ciclo = :cicloId " +
                        "AND NOT EXISTS " +
                        "(SELECT 1 FROM virtus.produtos_componentes pc WHERE pc.id_entidade = :entidadeId AND pc.id_ciclo = a.id_ciclo AND pc.id_pilar = a.id_pilar AND pc.id_componente = b.id_componente) " +
                        "GROUP BY a.id_ciclo, a.id_pilar, b.id_componente, c.peso_padrao ORDER BY 1, 2, 3, 4";

        return entityManager.createNativeQuery(jpql)
                .setParameter("entidadeId", entity != null ? entity.getId() : null)
                .setParameter("cicloId", cycle != null ? cycle.getId() : null)
                .setParameter("gradeTypeId", null)
                .setParameter("currentUser", current != null ? current.getId() : null)
                .setParameter("date", LocalDateTime.now())
                .executeUpdate();
    }

    private ProductPillar createProductPillar(User current, EntityVirtus entity, Cycle cycle) {
        ProductPillar productPillar = new ProductPillar();
        productPillar.setEntity(entity);
        productPillar.setCycle(cycle);
        productPillar.setWeight(BigDecimal.ZERO);
        productPillar.setGrade(BigDecimal.ZERO);

        PillarCycle pillarCycle = pillarCycleRepository.findByNotExistsInProductPillar(entity, cycle).orElse(null);
        if (pillarCycle != null) {
            productPillar.setPillar(pillarCycle.getPillar());
        }
        productPillar.setAuthor(current);

        return productPillarRepository.save(productPillar);
    }


    private CycleEntity parseToCycleEntity(EntityVirtusResponseDTO entityVirtus, Cycle cycle, LocalDate startsAt, LocalDate endsAt) {
        CycleEntity cycleEntity = new CycleEntity();
        cycleEntity.setStartsAt(startsAt);
        cycleEntity.setEndsAt(endsAt);
        cycleEntity.setEntity(entityVirtusRepository.findById(entityVirtus.getId()).orElse(null));
        cycleEntity.setCycle(cycle);
        cycleEntity.setCreatedAt(LocalDateTime.now());
        cycleEntity.setUpdatedAt(LocalDateTime.now());
        cycleEntity.setAuthor(getLoggedUser());

        CycleEntity persisted = cycle.getCycleEntities().stream().filter(ce -> entityVirtus.getId().equals(ce.getEntity().getId())).findFirst().orElse(null);
        if (persisted != null) {
            cycleEntity.setId(persisted.getId());
        }
        return cycleEntity;
    }

    public PageableResponseDTO<CycleResponseDTO> findCycleByEntityId(CurrentUser currentUser, Integer entityId, int page, int size){

        Page<Cycle> cyclePage = getRepository().findValidCyclesByEntityId(entityId, LocalDate.now(), PageRequest.of(page, size));

        List<CycleResponseDTO> content = cyclePage.getContent().stream().map( c ->parseToResponseDTO(c, false)).collect(Collectors.toList());

        return new PageableResponseDTO<>(
                content,
                page,
                size,
                cyclePage.getTotalPages(),
                cyclePage.getTotalElements());
    }
}
