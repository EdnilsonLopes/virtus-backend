package com.virtus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.ComponentRequestDTO;
import com.virtus.domain.dto.response.ComponentResponseDTO;
import com.virtus.domain.entity.Component;
import com.virtus.service.ComponentService;

@RestController
@RequestMapping("/components")
public class ComponentController
        extends BaseController<Component, ComponentService, ComponentRequestDTO, ComponentResponseDTO> {

    public ComponentController(ComponentService service) {
        super(service);
    }

}
