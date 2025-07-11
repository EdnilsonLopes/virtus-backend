package com.virtus.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

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
}
