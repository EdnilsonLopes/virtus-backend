package com.virtus.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.virtus.client.VirtusApiClient;
import com.virtus.client.dto.LastReferenceDTO;
import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.IndicatorScoreRequestDTO;
import com.virtus.domain.dto.response.IndicatorScoreResponseDTO;
import com.virtus.domain.entity.IndicatorScore;
import com.virtus.persistence.IndicatorScoreRepository;
import com.virtus.persistence.UserRepository;

@Service
public class IndicatorScoreService
        extends
        BaseService<IndicatorScore, IndicatorScoreRepository, IndicatorScoreRequestDTO, IndicatorScoreResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private VirtusApiClient indicatorApiClient;

    public IndicatorScoreService(IndicatorScoreRepository repository, UserRepository userRepository,
            EntityManagerFactory entityManagerFactory, VirtusApiClient indicatorApiClient) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
        this.indicatorApiClient = indicatorApiClient;
    }

    @Override
    protected IndicatorScoreResponseDTO parseToResponseDTO(IndicatorScore entity, boolean detailed) {
        return parseToIndicatorScoreResponseDTO(entity, detailed);
    }

    @Override
    protected IndicatorScore parseToEntity(IndicatorScoreRequestDTO body) {
        IndicatorScore entity = new IndicatorScore();
        entity.setId(body.getId());
        entity.setCnpb(body.getCnpb());
        entity.setIndicatorId(body.getIndicatorId());
        entity.setIndicatorSigla(body.getIndicatorSigla());
        entity.setReferenceDate(body.getReferenceDate());
        entity.setComponentText(body.getComponentText());
        entity.setScore(body.getScore());
        return entity;
    }

    @Override
    protected String getNotFoundMessage() {
        throw new UnsupportedOperationException("Unimplemented method 'getNotFoundMessage'");
    }

    @Transactional
    public String getLastReferenceFromConformity() {
        LastReferenceDTO dto = indicatorApiClient.fetchLastReference();
        return dto != null ? dto.getUltimaReferencia() : null;
    }

}
