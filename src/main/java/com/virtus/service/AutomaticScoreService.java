package com.virtus.service;

import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.AutomaticScoreRequestDTO;
import com.virtus.domain.dto.response.AutomaticScoreResponseDTO;
import com.virtus.domain.entity.AutomaticScore;
import com.virtus.persistence.AutomaticScoreRepository;
import com.virtus.persistence.UserRepository;

@Service
public class AutomaticScoreService extends
        BaseService<AutomaticScore, AutomaticScoreRepository, AutomaticScoreRequestDTO, AutomaticScoreResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    private static final Logger logger = Logger.getLogger(AutomaticScoreService.class.getName());

    public AutomaticScoreService(AutomaticScoreRepository repository,
            UserRepository userRepository,
            EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    protected AutomaticScoreResponseDTO parseToResponseDTO(AutomaticScore entity, boolean detailed) {
        AutomaticScoreResponseDTO dto = new AutomaticScoreResponseDTO();
        dto.setId(entity.getId());
        dto.setCnpb(entity.getCnpb());
        dto.setReferenceDate(entity.getReferenceDate());
        dto.setComponentId(entity.getComponentId());
        dto.setScore(entity.getScore());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    @Override
    protected AutomaticScore parseToEntity(AutomaticScoreRequestDTO body) {
        AutomaticScore entity = new AutomaticScore();
        entity.setId(body.getId());
        entity.setCnpb(body.getCnpb());
        entity.setReferenceDate(body.getReferenceDate());
        entity.setComponentId(body.getComponentId());
        entity.setScore(body.getScore());
        entity.setCreatedAt(body.getCreatedAt());
        return entity;
    }

    @Override
    protected String getNotFoundMessage() {
        return "Nota automática não encontrada.";
    }

    @Transactional
    public String calcularNotasAutomaticas(List<String> referencias) {
        if (referencias == null || referencias.isEmpty()) {
            logger.warning("Nenhuma referência informada.");
            return "Nenhuma referência informada.";
        }

        StringJoiner inClause = new StringJoiner(",", "'", "'");
        referencias.forEach(inClause::add);
        String clausulaIN = inClause.toString();

        String sql = "WITH NotasUnicas AS ( " +
                "    SELECT * FROM ( " +
                "        SELECT *, " +
                "               ROW_NUMBER() OVER (PARTITION BY CNPB, DATA_REFERENCIA, ID_INDICADOR ORDER BY CRIADO_EM DESC) AS rn "
                +
                "        FROM VIRTUS.NOTAS_INDICADORES " +
                "    ) t WHERE rn = 1 " +
                ") " +
                "INSERT INTO VIRTUS.NOTAS_AUTOMATICAS (CNPB, DATA_REFERENCIA, ID_COMPONENTE, NOTA, CRIADO_EM) " +
                "SELECT " +
                "    ni.CNPB, " +
                "    ni.DATA_REFERENCIA, " +
                "    ic.ID_COMPONENTE, " +
                "    SUM(ni.NOTA * ISNULL(ic.PESO_PADRAO, 1)) / NULLIF(SUM(ISNULL(ic.PESO_PADRAO, 1)), 0) AS NOTA_COMPONENTE, "
                +
                "    GETDATE() AS CRIADO_EM " +
                "FROM NotasUnicas ni " +
                "INNER JOIN VIRTUS.INDICADORES_COMPONENTES ic " +
                "    ON ni.ID_INDICADOR = ic.ID_INDICADOR " +
                "WHERE ni.NOTA <> 0 " +
                "  AND ni.DATA_REFERENCIA IN (%s) " +
                "GROUP BY ni.CNPB, ni.DATA_REFERENCIA, ic.ID_COMPONENTE " +
                "HAVING NOT EXISTS ( " +
                "    SELECT 1 FROM VIRTUS.NOTAS_AUTOMATICAS na " +
                "    WHERE na.CNPB = ni.CNPB " +
                "      AND na.DATA_REFERENCIA = ni.DATA_REFERENCIA " +
                "      AND na.ID_COMPONENTE = ic.ID_COMPONENTE " +
                ") " +
                "ORDER BY ni.CNPB, ni.DATA_REFERENCIA, ic.ID_COMPONENTE";
        sql = String.format(sql, clausulaIN);
        logger.info("Executando cálculo de notas automáticas para referências: " + referencias);
        logger.info(sql);

        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery(sql).executeUpdate();
            em.getTransaction().commit();
            return "Notas automáticas calculadas para referências: " + String.join(", ", referencias);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.severe("Erro ao executar SQL: " + e.getMessage());
            return "ERRO: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    public String getLastReferenceFromIndicatorsScores() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            String sql = "SELECT MAX(DATA_REFERENCIA) FROM VIRTUS.NOTAS_INDICADORES";
            return (String) em.createNativeQuery(sql).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar a última referência: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void calculateAll() {
        List<String> referencias = getRepository().findDistinctReferenceDates();
        for (String referencia : referencias) {
            // Reaproveita o método existente, se já tiver
            calcularNotasAutomaticas(List.of(referencia));
        }
    }

}
