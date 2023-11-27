package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.UserRequestDTO;
import com.virtus.domain.dto.request.UserUpdatePasswordRequestDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.dto.response.RoleResponseDTO;
import com.virtus.domain.dto.response.UserResponseDTO;
import com.virtus.domain.entity.Role;
import com.virtus.domain.entity.User;
import com.virtus.domain.model.CurrentUser;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.RoleRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User, UserRepository, UserRequestDTO, UserResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            UserRepository userRepository,
            EntityManagerFactory entityManagerFactory, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void validate(VirtusException ex, User currentUser, User entity) throws VirtusException {
        if (getRepository().existsByUsername(entity.getUsername())) {
            ex.addError(Translator.translate("username.used"));
        }
    }

    @Override
    protected UserResponseDTO parseToResponseDTO(User entity, boolean detailed) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUsername(entity.getUsername());
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMobile(entity.getMobile());
        dto.setRole(parseToRoleResponse(entity.getRole()));
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private RoleResponseDTO parseToRoleResponse(Role role) {
        RoleResponseDTO response = new RoleResponseDTO();
        response.setId(role.getId());
        response.setDescription(role.getDescription());
        response.setName(role.getName());
        return response;
    }

    @Override
    protected User parseToEntity(UserRequestDTO body) {
        User user = body.getId() != null ? getRepository().findById(body.getId()).orElse(new User()) : new User();
        user.setName(body.getName());
        user.setUsername(body.getUsername());
        user.setMobile(body.getMobile());
        user.setEmail(body.getEmail());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setRole(body.getRole() != null ? roleRepository.findById(body.getRole().getId()).orElse(null) : null);
        return user;
    }


    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("user.not.found");
    }

    public void updatePassword(CurrentUser currentUser, UserUpdatePasswordRequestDTO body) {
        User user = getRepository().findById(body.getUserId())
                .orElseThrow(() -> new VirtusException(Translator.translate("user.not.found")));

        if (body.getPassword().equals(body.getRepeatedPassword())) {
            user.setPassword(passwordEncoder.encode(body.getPassword()));
        } else {
            throw new VirtusException(Translator.translate("passwords.must.be.the.same"));
        }
        getRepository().save(user);
    }

    public PageableResponseDTO<UserResponseDTO> findAllNotMember(CurrentUser currentUser, int page, int size) {

        Page<User> userPage = getRepository().findAllNotMember(PageRequest.of(page, size));
        List<UserResponseDTO> content = userPage.getContent().stream().map(c -> parseToResponseDTO(c, false)).collect(Collectors.toList());

        return new PageableResponseDTO<>(
                content,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalPages(),
                userPage.getTotalElements());
    }
}
