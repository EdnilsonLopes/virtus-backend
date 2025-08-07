package com.virtus.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.CyclePillarRequestDTO;
import com.virtus.domain.dto.request.CycleRequestDTO;
import com.virtus.domain.dto.request.StartCycleRequestDTO;
import com.virtus.domain.dto.response.CyclePillarResponseDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.entity.Cycle;
import com.virtus.domain.entity.CycleEntity;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.entity.Pillar;
import com.virtus.domain.entity.PillarCycle;
import com.virtus.domain.entity.User;
import com.virtus.domain.model.CurrentUser;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.CycleEntityRepository;
import com.virtus.persistence.CycleRepository;
import com.virtus.persistence.EntityVirtusRepository;
import com.virtus.persistence.PillarCycleRepository;
import com.virtus.persistence.ProductComponentRepository;
import com.virtus.persistence.ProductCycleRepository;
import com.virtus.persistence.ProductPillarRepository;
import com.virtus.persistence.ProductPurgeRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;

@Service
public class CycleService extends BaseService<Cycle, CycleRepository, CycleRequestDTO, CycleResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final CycleEntityRepository cycleEntityRepository;
    private final ProductCycleRepository productCycleRepository;
    private final ProductPillarRepository productPillarRepository;
    private final PillarCycleRepository pillarCycleRepository;
    private final ProductComponentRepository productComponentRepository;
    private final ProductPurgeRepository purgeRepository;

    @PersistenceContext
    private EntityManager entityManager;
    private final EntityVirtusRepository entityVirtusRepository;

    public CycleService(CycleRepository repository, UserRepository userRepository,
            EntityManagerFactory entityManagerFactory,
            CycleEntityRepository cycleEntityRepository,
            ProductCycleRepository productCycleRepository,
            ProductPillarRepository productPillarRepository,
            PillarCycleRepository pillarCycleRepository,
            ProductComponentRepository productComponentRepository,
            ProductPurgeRepository purgeRepository,
            EntityVirtusRepository entityVirtusRepository) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
        this.cycleEntityRepository = cycleEntityRepository;
        this.productCycleRepository = productCycleRepository;
        this.productPillarRepository = productPillarRepository;
        this.pillarCycleRepository = pillarCycleRepository;
        this.productComponentRepository = productComponentRepository;
        this.entityVirtusRepository = entityVirtusRepository;
        this.purgeRepository = purgeRepository;
    }

    @Override
    protected CycleResponseDTO parseToResponseDTO(Cycle entity, boolean detailed) {
        CycleResponseDTO response = new CycleResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        response.setCreatedAt(entity.getCreatedAt());
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
        return cycleEntities.stream().map(ce -> parseToEntityVirtusResponse(ce.getEntity()))
                .collect(Collectors.toList());
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
        cycle.getPillarCycles().clear();
        cycle.getPillarCycles().addAll(parseToCyclePillars(body.getCyclePillars(), cycle));
        return cycle;
    }

    private List<PillarCycle> parseToCyclePillars(List<CyclePillarRequestDTO> cyclePillars, Cycle cycle) {
        if (CollectionUtils.isEmpty(cyclePillars)) {
            return new ArrayList<>();
        }
        return cyclePillars.stream().map(dto -> parseToCyclePillar(dto, cycle)).collect(Collectors.toList());
    }

    private PillarCycle parseToCyclePillar(CyclePillarRequestDTO dto, Cycle cycle) {
        PillarCycle pillarCycle = dto.getId() != null
                ? pillarCycleRepository.findById(dto.getId()).orElse(new PillarCycle())
                : new PillarCycle();
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
        Cycle cycle = getRepository().findById(body.getCycle().getId())
                .orElseThrow(() -> new VirtusException(Translator.translate("cycle.not.found")));
        cycle.setName(body.getCycle().getName());
        cycle.setDescription(body.getCycle().getDescription());
        cycle.setUpdatedAt(LocalDateTime.now());

        List<CycleEntity> cycleEntities = body.getEntities()
                .stream()
                .map(entityVirtus -> parseToCycleEntity(entityVirtus, cycle, body.getStartsAt(), body.getEndsAt()))
                .collect(Collectors.toList());
        AtomicReference<Integer> maxId = new AtomicReference<>(cycleEntityRepository.findMaxId());
        cycleEntities.forEach(cycleEntity -> {
            if (cycleEntity.getId() == null) {
                maxId.set(maxId.get() + 1);
                cycleEntity.setId(maxId.get());
            }
        });
        cycleEntityRepository.saveAll(cycleEntities);
        final User current = user;
        // removeProductCycles(cycle);
        // removeProductPillar(cycle);
        // removeProductComponent(cycle);
        for (CycleEntity cycleEntity : cycleEntities) {
            createProductCycle(current, cycleEntity.getEntity(), cycle);
            createProductPillar(current, cycleEntity.getEntity(), cycle);
            createProductComponent(current, cycleEntity.getEntity(), cycle);
        }
        getRepository().save(cycle);
    }

    @Transactional
    public void startCycle(CurrentUser currentUser, List<CycleEntity> cycleEntities) {
        User user = null;
        if (currentUser != null && currentUser.getId() != null) {
            user = findUserById(currentUser.getId());
        }
        setLoggedUser(user);

        final User current = user;
        for (CycleEntity cycleEntity : cycleEntities) {
            /*
             * removeProductCycles(cycleEntity.getCycle());
             * removeProductPillar(cycleEntity.getCycle());
             * removeProductComponent(cycleEntity.getCycle());
             */
            createProductCycle(current, cycleEntity.getEntity(), cycleEntity.getCycle());
            createProductPillar(current, cycleEntity.getEntity(), cycleEntity.getCycle());
            createProductComponent(current, cycleEntity.getEntity(), cycleEntity.getCycle());
            getRepository().save(cycleEntity.getCycle());
        }
    }

    @Override
    protected void beforeCreate(Cycle entity) {
        setDetailsId(entity);
    }

    @Override
    protected void beforeUpdate(Cycle entity) {
        setDetailsId(entity);
    }

    private void setDetailsId(Cycle entity) {
        if (!CollectionUtils.isEmpty(entity.getPillarCycles())) {
            AtomicReference<Integer> maxId = new AtomicReference<>(pillarCycleRepository.findMaxId());
            entity.getPillarCycles().forEach(pillarCycle -> {
                if (pillarCycle.getId() == null) {
                    pillarCycle.setId(maxId.updateAndGet(v -> v + 1));
                }
            });
        }
    }

    @Override
    protected void afterUpdate(Cycle entity) {
        pillarCycleRepository.deleteByCycleId(null);
    }

    private void removeProductCycles(Cycle cycle) {
        if (!CollectionUtils.isEmpty(cycle.getProductsCycles())) {
            productCycleRepository.deleteAllByIdInBatch(
                    cycle.getProductsCycles().stream().map(p -> p.getId()).collect(Collectors.toList()));
        }
    }

    private void removeProductPillar(Cycle cycle) {
        if (!CollectionUtils.isEmpty(cycle.getProductsPillars())) {
            productPillarRepository.deleteAllByIdInBatch(
                    cycle.getProductsPillars().stream().map(p -> p.getId()).collect(Collectors.toList()));
        }
    }

    private void removeProductComponent(Cycle cycle) {
        if (!CollectionUtils.isEmpty(cycle.getProductsComponents())) {
            productComponentRepository.deleteAllByIdInBatch(
                    cycle.getProductsComponents().stream().map(p -> p.getId()).collect(Collectors.toList()));
        }
    }

    private void createProductCycle(User user, EntityVirtus entity, Cycle cycle) {
        Integer entidadeId = entity != null ? entity.getId() : null;
        Integer cicloId = cycle != null ? cycle.getId() : null;

        if (entidadeId == null || cicloId == null)
            return;

        Long count = entityManager.createQuery(
                "SELECT COUNT(pc) FROM ProductCycle pc " +
                        "WHERE pc.entity.id = :entidadeId AND pc.cycle.id = :cicloId",
                Long.class)
                .setParameter("entidadeId", entidadeId)
                .setParameter("cicloId", cicloId)
                .getSingleResult();

        if (count > 0) {
            return; // Já existe
        }

        // Faz o insert
        String insertQuery = "INSERT INTO virtus.produtos_ciclos ( " +
                " id_entidade, id_ciclo, nota, id_tipo_pontuacao, id_author, criado_em ) " +
                "OUTPUT INSERTED.id_produto_ciclo " +
                "VALUES (:entidadeId, :cicloId, 0, :gradeTypeId, :currentUserId, GETDATE())";

        entityManager.createNativeQuery(insertQuery)
                .setParameter("entidadeId", entidadeId)
                .setParameter("cicloId", cicloId)
                .setParameter("gradeTypeId", null)
                .setParameter("currentUserId", user != null ? user.getId() : null)
                .getResultList();
    }

    @Transactional
    public void createProductComponent(User current, EntityVirtus entity, Cycle cycle) {
        Integer entidadeId = entity != null ? entity.getId() : null;
        Integer cicloId = cycle != null ? cycle.getId() : null;

        if (entidadeId == null || cicloId == null)
            return;

        // Verifica se já existem componentes criados
        String existsQuery = "SELECT COUNT(*) FROM virtus.produtos_componentes " +
                "WHERE id_entidade = :entidadeId AND id_ciclo = :cicloId";

        Number count = (Number) entityManager.createNativeQuery(existsQuery)
                .setParameter("entidadeId", entidadeId)
                .setParameter("cicloId", cicloId)
                .getSingleResult();

        if (count.longValue() > 0) {
            return; // Já existem registros para essa entidade/ciclo
        }

        // Faz o insert
        String insertQuery = "INSERT INTO virtus.produtos_componentes " +
                "(id_entidade, id_ciclo, id_pilar, id_componente, peso, nota, id_tipo_pontuacao, id_author, criado_em) "
                +
                "SELECT :entidadeId, :cicloId, a.id_pilar, b.id_componente, " +
                "CASE WHEN b.peso_padrao <> 0 THEN ROUND(AVG(b.peso_padrao), 2) ELSE 1 END, " +
                "0, :gradeTypeId, :currentUser, :date " +
                "FROM virtus.pilares_ciclos a " +
                "LEFT JOIN virtus.componentes_pilares b ON a.id_pilar = b.id_pilar " +
                "WHERE a.id_ciclo = :cicloId " +
                "GROUP BY a.id_ciclo, a.id_pilar, b.id_componente, b.peso_padrao";

        entityManager.createNativeQuery(insertQuery)
                .setParameter("entidadeId", entidadeId)
                .setParameter("cicloId", cicloId)
                .setParameter("gradeTypeId", null)
                .setParameter("currentUser", current != null ? current.getId() : null)
                .setParameter("date", LocalDateTime.now())
                .executeUpdate();
    }

    @Transactional
    private void createProductPillar(User current, EntityVirtus entity, Cycle cycle) {
        Integer entidadeId = entity != null ? entity.getId() : null;
        Integer cicloId = cycle != null ? cycle.getId() : null;

        if (entidadeId == null || cicloId == null)
            return;

        // Verifica se já existem pilares criados
        String existsQuery = "SELECT COUNT(*) FROM virtus.produtos_pilares " +
                "WHERE id_entidade = :entidadeId AND id_ciclo = :cicloId";

        Number count = (Number) entityManager.createNativeQuery(existsQuery)
                .setParameter("entidadeId", entidadeId)
                .setParameter("cicloId", cicloId)
                .getSingleResult();

        if (count.longValue() > 0) {
            return; // Já existem registros para essa entidade/ciclo
        }

        // Faz o insert
        String insertQuery = "INSERT INTO virtus.produtos_pilares " +
                "(id_entidade, id_ciclo, id_pilar, peso, nota, id_tipo_pontuacao, id_author, criado_em) " +
                "OUTPUT INSERTED.id_produto_pilar " +
                "SELECT :entidadeId, :cicloId, a.id_pilar, 0, 0, :gradeTypeId, :currentUser, GETDATE() " +
                "FROM virtus.pilares_ciclos a " +
                "WHERE a.id_ciclo = :cicloId";

        entityManager.createNativeQuery(insertQuery)
                .setParameter("entidadeId", entidadeId)
                .setParameter("cicloId", cicloId)
                .setParameter("gradeTypeId", null)
                .setParameter("currentUser", current != null ? current.getId() : null)
                .getResultList();
    }

    private CycleEntity parseToCycleEntity(EntityVirtusResponseDTO entityVirtus, Cycle cycle, LocalDate startsAt,
            LocalDate endsAt) {
        CycleEntity cycleEntity = cycle.getCycleEntities()
                .stream()
                .filter(ce -> entityVirtus.getId().equals(ce.getEntity().getId()))
                .findFirst()
                .orElseGet(() -> {
                    CycleEntity newEntity = new CycleEntity();
                    newEntity.setEntity(entityVirtusRepository.findById(entityVirtus.getId()).orElse(null));
                    return newEntity;
                });

        cycleEntity.setStartsAt(startsAt);
        cycleEntity.setEndsAt(endsAt);
        cycleEntity.setCycle(cycle);
        cycleEntity.setCreatedAt(LocalDateTime.now());
        cycleEntity.setUpdatedAt(LocalDateTime.now());
        cycleEntity.setAuthor(getLoggedUser());

        return cycleEntity;
    }

    public PageableResponseDTO<CycleResponseDTO> findCycleByEntityId(CurrentUser currentUser, Integer entityId,
            int page, int size) {

        Page<Cycle> cyclePage = getRepository().findValidCyclesByEntityId(entityId, LocalDate.now(),
                PageRequest.of(page, size));

        List<CycleResponseDTO> content = cyclePage.getContent().stream().map(c -> parseToResponseDTO(c, false))
                .collect(Collectors.toList());

        return new PageableResponseDTO<>(
                content,
                page,
                size,
                cyclePage.getTotalPages(),
                cyclePage.getTotalElements());
    }

    @Transactional
    public void removeCycleProducts(CurrentUser currentUser, StartCycleRequestDTO dto) {
        Integer cycleId = dto.getCycle().getId();

        // 1. Coleta todos os IDs de entidades atualmente vinculadas ao ciclo
        List<Integer> allEntityIds = cycleEntityRepository.findEntityIdsByCycleId(cycleId);

        // 2. Coleta os IDs de entidades que devem ser preservadas (vieram no DTO)
        List<Integer> preservedIds = Optional.ofNullable(dto.getEntities())
                .orElse(List.of())
                .stream()
                .map(e -> e.getId())
                .collect(Collectors.toList());

        // 3. Calcula quais entidades devem ser removidas (não vieram no DTO)
        List<Integer> toRemoveIds = allEntityIds.stream()
                .filter(id -> !preservedIds.contains(id))
                .collect(Collectors.toList());

        // 4. Executa a purga para cada entidade a ser removida
        for (Integer entityId : toRemoveIds) {
            purgeRepository.deleteProdutosItens(entityId, cycleId);
            purgeRepository.deleteProdutosElementos(entityId, cycleId);
            purgeRepository.deleteProdutosTiposNotas(entityId, cycleId);
            purgeRepository.deleteProdutosPlanos(entityId, cycleId);
            purgeRepository.deleteProdutosComponentes(entityId, cycleId);
            purgeRepository.deleteProdutosPilares(entityId, cycleId);
            purgeRepository.deleteProdutosCiclos(entityId, cycleId);
            purgeRepository.deleteCiclosEntidades(entityId, cycleId);
            purgeRepository.deleteProdutosItensHistoricos(entityId, cycleId);
            purgeRepository.deleteProdutosElementosHistoricos(entityId, cycleId);
            purgeRepository.deleteProdutosPlanosHistoricos(entityId, cycleId);
            purgeRepository.deleteProdutosComponentesHistoricos(entityId, cycleId);
            purgeRepository.deleteProdutosPilaresHistoricos(entityId, cycleId);
            purgeRepository.deleteProdutosCiclosHistoricos(entityId, cycleId);
            purgeRepository.deleteIntegrantes(entityId, cycleId);
        }
    }

}
