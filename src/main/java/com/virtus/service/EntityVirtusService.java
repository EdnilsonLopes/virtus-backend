package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.EntityVirtusRequestDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.persistence.EntityVirtusRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
public class EntityVirtusService extends BaseService<EntityVirtus, EntityVirtusRepository, EntityVirtusRequestDTO, EntityVirtusResponseDTO> {

    public EntityVirtusService(EntityVirtusRepository repository,
                               UserRepository userRepository,
                               EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
    }

    @Override
    protected EntityVirtusResponseDTO parseToResponseDTO(EntityVirtus entity, boolean detailed) {
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

    @Override
    protected EntityVirtus parseToEntity(EntityVirtusRequestDTO body) {
        EntityVirtus entity = new EntityVirtus();
        entity.setId(body.getId());
        entity.setDescription(body.getDescription());
        entity.setUf(body.getUf());
        entity.setName(body.getName());
        entity.setCity(body.getCity());
        entity.setAcronym(body.getAcronym());
        entity.setCode(body.getCode());
        entity.setEsi(body.getEsi());
        entity.setSituation(body.getSituation());
        return entity;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("entity.not.found");
    }
}
