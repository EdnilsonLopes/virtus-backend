package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.FeatureRequestDTO;
import com.virtus.domain.dto.response.FeatureResponseDTO;
import com.virtus.domain.entity.Feature;
import com.virtus.persistence.FeatureRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
public class FeatureService extends BaseService<Feature, FeatureRepository, FeatureRequestDTO, FeatureResponseDTO> {


    public FeatureService(FeatureRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
    }

    @Override
    protected FeatureResponseDTO parseToResponseDTO(Feature entity, boolean detailed) {
        FeatureResponseDTO response = new FeatureResponseDTO();
        response.setCode(entity.getCode());
        response.setDescription(entity.getDescription());
        response.setName(entity.getName());
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        return response;
    }

    @Override
    protected Feature parseToEntity(FeatureRequestDTO body) {
        Feature entity = new Feature();
        entity.setId(body.getId());
        entity.setName(body.getName());
        entity.setCode(body.getCode());
        entity.setDescription(body.getDescription());
        return entity;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("feature.not.found");
    }
}
