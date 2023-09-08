package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.StatusRequestDTO;
import com.virtus.domain.dto.response.StatusResponseDTO;
import com.virtus.domain.entity.Status;
import com.virtus.persistence.StatusRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
public class StatusService extends BaseService<Status, StatusRepository, StatusRequestDTO, StatusResponseDTO> {

    public StatusService(StatusRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
    }

    @Override
    protected StatusResponseDTO parseToResponseDTO(Status entity, boolean detailed) {
        StatusResponseDTO response = new StatusResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setStereotype(entity.getStereotype());
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        response.setCreatedAt(entity.getCreatedAt());
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        return response;
    }

    @Override
    protected Status parseToEntity(StatusRequestDTO body) {
        Status status = new Status();
        status.setId(body.getId());
        status.setName(body.getName());
        status.setStereotype(body.getStereotype());
        status.setDescription(body.getDescription());
        return status;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("status.not.found");
    }
}
