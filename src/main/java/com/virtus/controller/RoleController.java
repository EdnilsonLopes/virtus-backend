package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.RoleRequestDTO;
import com.virtus.domain.dto.response.RoleResponseDTO;
import com.virtus.domain.entity.Role;
import com.virtus.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<Role, RoleService, RoleRequestDTO, RoleResponseDTO> {

    public RoleController(RoleService service) {
        super(service);
    }
}
