package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.RoleRequestDTO;
import com.virtus.domain.dto.response.FeatureResponseDTO;
import com.virtus.domain.dto.response.FeatureRoleResponseDTO;
import com.virtus.domain.dto.response.RoleResponseDTO;
import com.virtus.domain.entity.Feature;
import com.virtus.domain.entity.FeatureRole;
import com.virtus.domain.entity.Role;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.FeatureRepository;
import com.virtus.persistence.RoleRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class RoleService extends BaseService<Role, RoleRepository, RoleRequestDTO, RoleResponseDTO> {

    private final FeatureRepository featureRepository;

    public RoleService(RoleRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory, FeatureRepository featureRepository) {
        super(repository, userRepository, entityManagerFactory);
        this.featureRepository = featureRepository;
    }

    @Override
    protected RoleResponseDTO parseToResponseDTO(Role entity, boolean detailed) {
        RoleResponseDTO response = new RoleResponseDTO();
        response.setDescription(entity.getDescription());
        response.setName(entity.getName());
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        if (detailed) {
            if (CollectionUtils.isEmpty(entity.getFeatures())) {
                response.setFeatures(new ArrayList<>());
            } else {
                response.setFeatures(entity.getFeatures().stream()
                        .map(featureRole -> parseToFeatureRoleResponse(featureRole)).collect(Collectors.toList()));
            }
        }
        return response;
    }

    private FeatureRoleResponseDTO parseToFeatureRoleResponse(FeatureRole featureRole) {
        FeatureRoleResponseDTO responseDTO = new FeatureRoleResponseDTO();
        responseDTO.setId(featureRole.getId());
        responseDTO.setFeature(parseToFeatureResponse(featureRole.getFeature()));
        return responseDTO;
    }

    private FeatureResponseDTO parseToFeatureResponse(Feature feature) {
        FeatureResponseDTO response = new FeatureResponseDTO();
        response.setCode(feature.getCode());
        response.setDescription(feature.getDescription());
        response.setName(feature.getName());
        response.setId(feature.getId());
        response.setCreatedAt(feature.getCreatedAt());
        response.setUpdatedAt(feature.getUpdatedAt());
        response.setAuthor(parseToUserResponseDTO(feature.getAuthor()));
        return response;
    }

    @Override
    protected Role parseToEntity(RoleRequestDTO body) {
        Role role = body.getId() != null ? getRepository().findById(body.getId()).orElse(new Role()): new Role();
        role.setId(body.getId());
        role.setName(body.getName());
        role.setDescription(body.getDescription());
        role.getFeatures().clear();
        role.getFeatures().addAll(parseToFeatureRole(body, role));
        return role;
    }

    private List<FeatureRole> parseToFeatureRole(RoleRequestDTO body, Role role) {
        if (CollectionUtils.isEmpty(body.getFeatures())) {
            return new ArrayList<>();
        }
        return body.getFeatures().stream().map(featureRoleRequestDTO -> {
            FeatureRole featureRole = new FeatureRole();
            featureRole.setId(featureRoleRequestDTO.getId());
            featureRole.setRole(role);
            featureRole.setFeature(featureRepository.findById(featureRoleRequestDTO.getFeature().getId()).orElse(null));
            return featureRole;
        }).collect(Collectors.toList());
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("role.not.found");
    }

    @Override
    protected void beforeCreate(Role entity) {
        setDetailsId(entity);
    }

    private void setDetailsId(Role entity) {
        if (!CollectionUtils.isEmpty(entity.getFeatures())) {
            AtomicReference<Integer> maxId = new AtomicReference<>(featureRepository.findMaxId());
            entity.getFeatures().forEach(featureRole -> {
                if (featureRole.getId() == null) {
                    featureRole.setId(maxId.updateAndGet(v -> v + 1));
                }
            });
        }
    }

    @Override
    protected void beforeUpdate(Role entity) {
        setDetailsId(entity);
    }
}
