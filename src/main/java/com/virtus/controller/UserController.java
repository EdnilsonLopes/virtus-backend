package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.UserRequestDTO;
import com.virtus.domain.dto.response.UserResponseDTO;
import com.virtus.domain.entity.User;
import com.virtus.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, UserService, UserRequestDTO, UserResponseDTO> {

    public UserController(UserService service) {
        super(service);
    }
}
