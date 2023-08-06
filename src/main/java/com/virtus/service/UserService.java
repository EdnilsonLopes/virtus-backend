package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.UserRequestDTO;
import com.virtus.domain.dto.response.UserResponseDTO;
import com.virtus.domain.entity.User;
import com.virtus.persistence.RoleRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Service
public class UserService extends BaseService<User, UserRepository, UserRequestDTO, UserResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final RoleRepository roleRepository;
    //private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            UserRepository userRepository,
            EntityManagerFactory entityManagerFactory, RoleRepository roleRepository
            //PasswordEncoder passwordEncoder
    ) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
        this.roleRepository = roleRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected UserResponseDTO parseToResponseDTO(User entity, boolean detailed) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUsername(entity.getUsername());
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    @Override
    protected User parseToEntity(UserRequestDTO body) {
        User user = new User();
        user.setId(body.getId());
        user.setName(body.getName());
        user.setUsername(body.getUsername());
        //user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setPassword(body.getPassword());
        //user.setRoles(Arrays.asList(roleRepository.findByName(body.getName()).get()));
        return user;
    }


    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("user.not.found");
    }
}
