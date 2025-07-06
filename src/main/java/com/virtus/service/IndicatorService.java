package com.virtus.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.IndicatorRequestDTO;
import com.virtus.domain.dto.response.IndicatorResponseDTO;
import com.virtus.domain.entity.Indicator;
import com.virtus.persistence.IndicatorRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;

@Service
public class IndicatorService extends BaseService<
        Indicator,
        IndicatorRepository,
        IndicatorRequestDTO,
        IndicatorResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    public IndicatorService(IndicatorRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
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
}
