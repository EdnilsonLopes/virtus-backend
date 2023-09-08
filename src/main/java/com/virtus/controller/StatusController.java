package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.StatusRequestDTO;
import com.virtus.domain.dto.response.StatusResponseDTO;
import com.virtus.domain.entity.Status;
import com.virtus.service.StatusService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/status")
public class StatusController extends BaseController<Status, StatusService, StatusRequestDTO, StatusResponseDTO> {

    public StatusController(StatusService service) {
        super(service);
    }
}
