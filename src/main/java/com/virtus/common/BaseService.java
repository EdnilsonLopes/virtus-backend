package com.virtus.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.common.domain.entity.BaseEntity;
import com.virtus.domain.dto.EnumDTO;
import com.virtus.domain.dto.response.ComponentElementResponseDTO;
import com.virtus.domain.dto.response.ComponentGradeTypeResponseDTO;
import com.virtus.domain.dto.response.ComponentIndicatorResponseDTO;
import com.virtus.domain.dto.response.ComponentResponseDTO;
import com.virtus.domain.dto.response.ElementItemResponseDTO;
import com.virtus.domain.dto.response.ElementResponseDTO;
import com.virtus.domain.dto.response.GradeTypeResponseDTO;
import com.virtus.domain.dto.response.IndicatorResponseDTO;
import com.virtus.domain.dto.response.IndicatorScoreResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.dto.response.PillarComponentResponseDTO;
import com.virtus.domain.dto.response.PillarResponseDTO;
import com.virtus.domain.dto.response.RoleResponseDTO;
import com.virtus.domain.dto.response.StatusResponseDTO;
import com.virtus.domain.dto.response.UserResponseDTO;
import com.virtus.domain.entity.Component;
import com.virtus.domain.entity.ComponentPillar;
import com.virtus.domain.entity.Element;
import com.virtus.domain.entity.ElementComponent;
import com.virtus.domain.entity.ElementItem;
import com.virtus.domain.entity.GradeType;
import com.virtus.domain.entity.GradeTypeComponent;
import com.virtus.domain.entity.Indicator;
import com.virtus.domain.entity.IndicatorComponent;
import com.virtus.domain.entity.IndicatorScore;
import com.virtus.domain.entity.Pillar;
import com.virtus.domain.entity.Role;
import com.virtus.domain.entity.Status;
import com.virtus.domain.entity.User;
import com.virtus.domain.enums.AverageType;
import com.virtus.domain.model.CurrentUser;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;

public abstract class BaseService<T extends BaseEntity, R extends BaseRepository<T>, C extends BaseRequestDTO, DTO extends BaseResponseDTO> {

    public final VirtusException ERROR_USER_NOT_FOUND = new VirtusException(Translator.translate("user.not.found"));

    private final R repository;
    private final UserRepository userRepository;

    private User loggedUser;

    private final EntityManagerFactory entityManagerFactory;

    public BaseService(R repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    public PageableResponseDTO<DTO> findAll(CurrentUser currentUser, int page, int size) {
        Page<T> pageResult = findAll(page, size);
        List<DTO> content = pageResult.getContent().stream().map(c -> parseToResponseDTO(c, false))
                .collect(Collectors.toList());

        return new PageableResponseDTO<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements());
    }

    public PageableResponseDTO<DTO> findAllByFilter(CurrentUser currentUser, String filter, int page, int size) {
        Page<T> pageResult = findAllByFilter(filter, page, size);
        List<DTO> content = pageResult.getContent().stream().map(c -> parseToResponseDTO(c, false))
                .collect(Collectors.toList());

        return new PageableResponseDTO<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements());
    }

    public DTO findById(CurrentUser currentUser, Integer id) {
        T result = findById(id).orElseThrow(() -> new VirtusException(getNotFoundMessage()));
        DTO response = parseToResponseDTO(result, true);
        response.setCreatedAt(result.getCreatedAt());
        response.setUpdatedAt(result.getUpdatedAt());
        return response;
    }

    public DTO create(CurrentUser currentUser, C body) {
        User user = null;
        if (currentUser != null && currentUser.getId() != null) {
            user = findUserById(currentUser.getId());
            setLoggedUser(user);
        }
        setLoggedUser(user);
        VirtusException ex = new VirtusException();
        T entity = parseToEntity(body);
        validate(ex, user, entity);
        if (ex.hasErrors()) {
            throw ex;
        }
        entity.setAuthor(user);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        beforeCreate(entity);
        save(entity);
        afterCreate(entity);
        DTO response = parseToResponseDTO(entity, true);
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    public DTO update(CurrentUser currentUser, C body) {
        User user = null;
        if (currentUser != null && currentUser.getId() != null) {
            user = findUserById(currentUser.getId());
            setLoggedUser(user);
        }
        setLoggedUser(user);
        VirtusException ex = new VirtusException();
        T entity = parseToEntity(body);
        validate(ex, user, entity);
        if (ex.hasErrors()) {
            throw ex;
        }
        entity.setUpdatedAt(LocalDateTime.now());
        beforeUpdate(entity);
        save(entity);
        afterUpdate(entity);
        DTO response = parseToResponseDTO(entity, true);
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    public void delete(CurrentUser currentUser, Integer id) {
        User user = null;
        if (currentUser != null && currentUser.getId() != null) {
            user = findUserById(currentUser.getId());
            setLoggedUser(user);
        }
        setLoggedUser(user);
        VirtusException ex = new VirtusException();
        validateOnDelete(ex, user, id);
        if (ex.hasErrors()) {
            throw ex;
        }
        beforeDelete(id);
        delete(id);
        afterDelete(id);
    }

    protected UserResponseDTO parseToUserResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setRole(parseToRoleResponse(user.getRole()));
        return dto;
    }

    private RoleResponseDTO parseToRoleResponse(Role role) {
        RoleResponseDTO response = new RoleResponseDTO();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDescription(role.getDescription());
        return response;
    }

    protected ElementResponseDTO parseToElementResponseDTO(Element entity, boolean detailed) {
        ElementResponseDTO dto = new ElementResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setReference(entity.getReference());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        dto.setOrdination(entity.getOrdination());
        if (detailed)
            dto.setItems(parseToElementItemsDTO(entity.getItems()));
        return dto;
    }

    private List<ElementItemResponseDTO> parseToElementItemsDTO(List<ElementItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            return new ArrayList<>();
        }
        return items.stream().map(this::parseToElementItemDTO).collect(Collectors.toList());

    }

    private ElementItemResponseDTO parseToElementItemDTO(ElementItem elementItem) {
        ElementItemResponseDTO dto = new ElementItemResponseDTO();
        dto.setId(elementItem.getId());
        dto.setName(elementItem.getName());
        dto.setDescription(elementItem.getDescription());
        dto.setReference(elementItem.getReference());
        dto.setCreatedAt(elementItem.getCreatedAt());
        dto.setUpdatedAt(elementItem.getUpdatedAt());
        return dto;
    }

    protected GradeTypeResponseDTO parseToGradeTypeResponseDTO(GradeType entity, boolean detailed) {
        GradeTypeResponseDTO dto = new GradeTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setLetter(entity.getLetter());
        dto.setOrdination(entity.getOrdination());
        dto.setReference(entity.getReference());
        dto.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        dto.setLetterColor(entity.getLetterColor());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    protected ComponentResponseDTO parseToComponentResponse(Component entity, boolean detailed) {
        ComponentResponseDTO response = new ComponentResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setOrdination(entity.getOrdination());
        response.setPga(entity.getPga());
        response.setDescription(entity.getDescription());
        response.setReference(entity.getReference());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        if (detailed) {
            response.setComponentElements(parseToComponentElementsResponse(entity.getElementComponents()));
            response.setComponentGradeTypes(parseToComponentGradeTypesResponse(entity.getGradeTypeComponents()));
            response.setComponentIndicators(parseToComponentIndicatorsResponse(entity.getIndicatorComponents()));
        }
        return response;
    }

    private List<ComponentIndicatorResponseDTO> parseToComponentIndicatorsResponse(
            List<IndicatorComponent> indicatorComponents) {
        if (CollectionUtils.isEmpty(indicatorComponents)) {
            return new ArrayList<>();
        }
        return indicatorComponents.stream().map(this::parseToComponentIndicatorResponse).collect(Collectors.toList());
    }

    private ComponentIndicatorResponseDTO parseToComponentIndicatorResponse(IndicatorComponent indicatorComponent) {
        ComponentIndicatorResponseDTO response = new ComponentIndicatorResponseDTO();
        response.setId(indicatorComponent.getId());
        response.setStandardWeight(indicatorComponent.getStandardWeight());
        response.setAuthor(parseToUserResponseDTO(indicatorComponent.getAuthor()));
        response.setIndicator(parseToIndicatorResponseDTO(indicatorComponent.getIndicator(), false));
        response.setCreatedAt(indicatorComponent.getCreatedAt());
        response.setUpdatedAt(indicatorComponent.getUpdatedAt());
        return response;
    }

    protected IndicatorResponseDTO parseToIndicatorResponseDTO(Indicator entity, boolean detailed) {
        IndicatorResponseDTO dto = new IndicatorResponseDTO();
        dto.setId(entity.getId());
        dto.setIndicatorAcronym(entity.getIndicatorAcronym());
        dto.setIndicatorName(entity.getIndicatorName());
        dto.setIndicatorDescription(entity.getIndicatorDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        if (entity.getAuthor() != null) {
            dto.setAuthorName(entity.getAuthor().getName());
        }
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    protected IndicatorScoreResponseDTO parseToIndicatorScoreResponseDTO(IndicatorScore entity, boolean detailed) {
        IndicatorScoreResponseDTO dto = new IndicatorScoreResponseDTO();
        dto.setId(entity.getId());
        dto.setCnpb(entity.getCnpb());
        dto.setReferenceDate(entity.getReferenceDate());
        dto.setComponentText(entity.getComponentText());
        dto.setScore(entity.getScore());
        dto.setIndicatorId(entity.getIndicatorId());
        dto.setIndicatorSigla(entity.getIndicatorSigla());
        dto.setCreatedAt(entity.getCreatedAt());
        if (entity.getAuthor() != null) {
            dto.setAuthorName(entity.getAuthor().getName());
        }
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private List<ComponentGradeTypeResponseDTO> parseToComponentGradeTypesResponse(
            List<GradeTypeComponent> gradeTypeComponents) {
        if (CollectionUtils.isEmpty(gradeTypeComponents)) {
            return new ArrayList<>();
        }
        return gradeTypeComponents.stream().map(this::parseToComponentGradeTypeResponse).collect(Collectors.toList());
    }

    private ComponentGradeTypeResponseDTO parseToComponentGradeTypeResponse(GradeTypeComponent gradeTypeComponent) {
        ComponentGradeTypeResponseDTO response = new ComponentGradeTypeResponseDTO();
        response.setId(gradeTypeComponent.getId());
        response.setStandardWeight(gradeTypeComponent.getStandardWeight());
        response.setAuthor(parseToUserResponseDTO(gradeTypeComponent.getAuthor()));
        response.setGradeType(parseToGradeTypeResponseDTO(gradeTypeComponent.getGradeType(), false));
        response.setCreatedAt(gradeTypeComponent.getCreatedAt());
        response.setUpdatedAt(gradeTypeComponent.getUpdatedAt());
        return response;
    }

    private List<ComponentElementResponseDTO> parseToComponentElementsResponse(
            List<ElementComponent> elementComponents) {
        if (CollectionUtils.isEmpty(elementComponents)) {
            return new ArrayList<>();
        }
        return elementComponents.stream().map(this::parseToComponentElementResponse).collect(Collectors.toList());
    }

    private ComponentElementResponseDTO parseToComponentElementResponse(ElementComponent elementComponent) {
        ComponentElementResponseDTO response = new ComponentElementResponseDTO();
        response.setId(elementComponent.getId());
        response.setStandardWeight(elementComponent.getStandardWeight());
        response.setGradeType(parseToGradeTypeResponseDTO(elementComponent.getGradeType(), false));
        response.setAuthor(parseToUserResponseDTO(elementComponent.getAuthor()));
        response.setElement(parseToElementResponseDTO(elementComponent.getElement(), false));
        response.setCreatedAt(elementComponent.getCreatedAt());
        response.setUpdatedAt(elementComponent.getUpdatedAt());
        return response;
    }

    protected PillarResponseDTO parseToPillarResponseDTO(Pillar entity, boolean detailed) {
        PillarResponseDTO response = new PillarResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setOrdination(entity.getOrdination());
        response.setDescription(entity.getDescription());
        response.setReference(entity.getReference());
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        if (detailed)
            response.setComponents(parseToPillarComponentsResponse(entity.getComponentPillars()));
        return response;
    }

    private List<PillarComponentResponseDTO> parseToPillarComponentsResponse(List<ComponentPillar> componentPillars) {
        if (CollectionUtils.isEmpty(componentPillars)) {
            return new ArrayList<>();
        }
        return componentPillars.stream().map(this::parseToPillarComponentResponse).collect(Collectors.toList());
    }

    private PillarComponentResponseDTO parseToPillarComponentResponse(ComponentPillar componentPillar) {
        PillarComponentResponseDTO response = new PillarComponentResponseDTO();
        response.setId(componentPillar.getId());
        response.setAverageType(parseToAverageTypeEnumResponseDTO(componentPillar.getAverageType()));
        response.setAuthor(parseToUserResponseDTO(componentPillar.getAuthor()));
        response.setUpdatedAt(componentPillar.getUpdatedAt());
        response.setCreatedAt(componentPillar.getCreatedAt());
        response.setProbeFile(componentPillar.getProbeFile());
        response.setStandardWeight(componentPillar.getStandardWeight());
        response.setComponent(parseToComponentResponse(componentPillar.getComponent(), false));
        return response;
    }

    protected StatusResponseDTO parseToStatusResponseDTO(Status status) {
        StatusResponseDTO response = new StatusResponseDTO();
        response.setId(status.getId());
        response.setName(status.getName());
        response.setDescription(status.getDescription());
        response.setStereotype(status.getStereotype());
        response.setAuthor(parseToUserResponseDTO(status.getAuthor()));
        return response;
    }

    protected EnumDTO<AverageType> parseToAverageTypeEnumResponseDTO(AverageType averageType) {
        if (averageType == null) {
            return EnumDTO.<AverageType>builder().build();
        }
        return EnumDTO.<AverageType>builder()
                .value(averageType)
                .description(Translator.translate(averageType.getKey()))
                .build();
    }

    protected EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    protected abstract DTO parseToResponseDTO(T entity, boolean detailed);

    protected abstract T parseToEntity(C body);

    protected abstract String getNotFoundMessage();

    protected void completeDetails(T entity) {
        // Optional
    }

    protected void beforeCreate(T entity) {
        completeDetails(entity);
    }

    protected void beforeUpdate(T entity) {
        completeDetails(entity);
    }

    protected void beforeDelete(Integer entity) {
        // Optional
    }

    protected void afterCreate(T entity) {
        // Optional
    }

    protected void afterUpdate(T entity) {
        // Optional
    }

    protected void afterDelete(Integer entity) {
        // Optional
    }

    protected void validate(VirtusException ex, User user, T entity) throws VirtusException {
        // Optional
    }

    protected void validateOnDelete(VirtusException ex, User user, Integer id) throws VirtusException {
        // Optional
    }

    @Transactional
    protected T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(getRepository().findMaxId() + 1);
        }
        return getRepository().save(entity);
    }

    protected List<T> saveAll(List<T> entities) {
        return getRepository().saveAll(entities);
    }

    protected T saveAndFlush(T entity) {
        if (entity.getId() == null) {
            entity.setId(getRepository().findMaxId() + 1);
        }
        return getRepository().saveAndFlush(entity);
    }

    protected List<T> saveAllAndFlush(List<T> entities) {
        return getRepository().saveAllAndFlush(entities);
    }

    protected Optional<T> findById(Integer id) {
        return getRepository().findById(id);
    }

    protected Page<T> findAll(int page, int size) {
        return getRepository().findAll(PageRequest.of(page, size));
    }

    public Page<T> findAllByFilter(String filter, int page, int size) {
        return getRepository().findAllByFilter(filter, PageRequest.of(page, size));
    }

    protected Page<T> findAll(int page, int size, Sort sort) {
        return getRepository().findAll(PageRequest.of(page, size, sort));
    }

    protected void delete(T entity) {
        EntityManager em = createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception exception) {
            em.getTransaction().rollback();
            throw exception;
        } finally {
            em.close();
        }
    }

    protected void delete(Integer id) {
        getRepository().deleteById(id);
    }

    protected void deleteAll(List<T> entities) {
        getRepository().deleteAll(entities);
    }

    protected void deleteAllById(List<Integer> ids) {
        getRepository().deleteAllById(ids);
    }

    protected User findUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> ERROR_USER_NOT_FOUND);
        return user;
    }

    public R getRepository() {
        return repository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
