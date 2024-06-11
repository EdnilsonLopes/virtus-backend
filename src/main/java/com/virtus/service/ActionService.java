package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.ActionRequestDTO;
import com.virtus.domain.dto.response.ActionResponseDTO;
import com.virtus.domain.dto.response.StatusResponseDTO;
import com.virtus.domain.entity.Action;
import com.virtus.domain.entity.Status;
import com.virtus.persistence.ActionRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
public class ActionService extends BaseService<Action, ActionRepository, ActionRequestDTO, ActionResponseDTO> {

    private final StatusService statusService;

    public ActionService(ActionRepository repository,
                         UserRepository userRepository,
                         EntityManagerFactory entityManagerFactory,
                         StatusService statusService) {
        super(repository, userRepository, entityManagerFactory);
        this.statusService = statusService;
    }

    @Override
    protected ActionResponseDTO parseToResponseDTO(Action entity, boolean detailed) {
        ActionResponseDTO response = new ActionResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setOriginStatus(parseToStatusResponseDTO(entity.getOriginStatus()));
        response.setDestinationStatus(parseToStatusResponseDTO(entity.getDestinationStatus()));
        response.setOtherThan(entity.isOtherThan());
        return response;
    }

    @Override
    protected Action parseToEntity(ActionRequestDTO body) {
        Action action = new Action();
        action.setId(body.getId());
        action.setName(body.getName());
        action.setDescription(body.getDescription());
        action.setOriginStatus(statusService.getRepository().findById(body.getIdOriginStatus()).orElse(null));
        action.setDestinationStatus(statusService.getRepository().findById(body.getIdDestinationStatus()).orElse(null));
        action.setOtherThan(body.isOtherThan());
        return action;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("action.not.found");
    }

}
