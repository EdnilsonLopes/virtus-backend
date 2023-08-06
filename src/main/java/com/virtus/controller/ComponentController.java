package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.ComponentRequestDTO;
import com.virtus.domain.dto.response.ComponentResponseDTO;
import com.virtus.domain.entity.Component;
import com.virtus.service.ComponentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/components")
public class ComponentController extends BaseController<Component, ComponentService, ComponentRequestDTO, ComponentResponseDTO> {

    public ComponentController(ComponentService service) {
        super(service);
    }
}
