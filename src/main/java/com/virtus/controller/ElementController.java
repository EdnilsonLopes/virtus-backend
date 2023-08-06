package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.ElementRequestDTO;
import com.virtus.domain.dto.response.ElementResponseDTO;
import com.virtus.domain.entity.Element;
import com.virtus.service.ElementService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elements")
public class ElementController extends BaseController<
        Element,
        ElementService,
        ElementRequestDTO,
        ElementResponseDTO> {

    public ElementController(ElementService service) {
        super(service);
    }
}
