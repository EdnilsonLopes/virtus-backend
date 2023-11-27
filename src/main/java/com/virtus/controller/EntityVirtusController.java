package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.EntityVirtusRequestDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.EntityVirtusService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/entities")
public class EntityVirtusController extends BaseController<EntityVirtus, EntityVirtusService, EntityVirtusRequestDTO, EntityVirtusResponseDTO> {

    public EntityVirtusController(EntityVirtusService service) {
        super(service);
    }

}
