package com.virtus.service;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.virtus.client.VirtusApiClient;
import com.virtus.client.dto.ExternalIndicatorDTO;
import com.virtus.client.dto.LastReferenceDTO;
import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.IndicatorRequestDTO;
import com.virtus.domain.dto.response.IndicatorResponseDTO;
import com.virtus.domain.entity.Indicator;
import com.virtus.persistence.IndicatorRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;

@Service
public class IndicatorService
        extends BaseService<Indicator, IndicatorRepository, IndicatorRequestDTO, IndicatorResponseDTO> {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private VirtusApiClient indicatorApiClient;

    public IndicatorService(IndicatorRepository repository, UserRepository userRepository,
            EntityManagerFactory entityManagerFactory, VirtusApiClient indicatorApiClient) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
        this.indicatorApiClient = indicatorApiClient;
    }

    @Override
    protected IndicatorResponseDTO parseToResponseDTO(Indicator entity, boolean detailed) {
        return parseToIndicatorResponseDTO(entity, detailed);
    }

    @Override
    protected Indicator parseToEntity(IndicatorRequestDTO body) {
        Indicator entity = new Indicator();
        entity.setId(body.getId());
        entity.setIndicatorName(body.getIndicatorName());
        entity.setIndicatorAcronym(body.getIndicatorAcronym());
        entity.setIndicatorDescription(body.getIndicatorDescription());
        return entity;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("type.of.note.not.found");
    }

    @Transactional
    public void syncFromRemoteApi() {
        List<ExternalIndicatorDTO> externalIndicators = indicatorApiClient.fetchAllIndicators();
        for (ExternalIndicatorDTO dto : externalIndicators) {
            Indicator indicator = getRepository().findById(dto.getIdIndicador())
                    .orElse(new Indicator(dto.getIdIndicador()));

            indicator.setIndicatorAcronym(dto.getSgIndicador());
            indicator.setIndicatorName(dto.getDescrIndicador());
            indicator.setIndicatorDescription(dto.getDescrIndicador());

            getRepository().save(indicator);
        }
    }

}
