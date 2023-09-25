package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.UserRequestDTO;
import com.virtus.domain.dto.request.UserUpdatePasswordRequestDTO;
import com.virtus.domain.dto.response.UserResponseDTO;
import com.virtus.domain.entity.User;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, UserService, UserRequestDTO, UserResponseDTO> {

    public UserController(UserService service) {
        super(service);
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@LoggedUser CurrentUser currentUser, @RequestBody UserUpdatePasswordRequestDTO body) {

        getService().updatePassword(currentUser, body);
        return ResponseEntity.ok().build();
    }

}
