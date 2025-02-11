package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.UserRequestDTO;
import com.virtus.domain.dto.request.UserUpdatePasswordRequestDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.dto.response.UserResponseDTO;
import com.virtus.domain.entity.User;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/not-member")
    public ResponseEntity<PageableResponseDTO<UserResponseDTO>> getAllNotMember(
            @LoggedUser CurrentUser currentUser,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int size) {
        return ResponseEntity.ok(getService().findAllNotMember(currentUser, page, size));
    }

    @GetMapping("/by-role")
    public ResponseEntity<List<UserResponseDTO>> getAllByRole(
            @LoggedUser CurrentUser currentUser,
            @RequestParam int roleId) {
        return ResponseEntity.ok(getService().findAllByRole(currentUser, roleId));
    }

}
