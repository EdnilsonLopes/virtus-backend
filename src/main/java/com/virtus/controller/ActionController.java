package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.ActionRequestDTO;
import com.virtus.domain.dto.response.ActionResponseDTO;
import com.virtus.domain.entity.Action;
import com.virtus.service.ActionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/actions")
public class ActionController extends BaseController<Action, ActionService, ActionRequestDTO, ActionResponseDTO> {

    public ActionController(ActionService service) {
        super(service);
    }
}
