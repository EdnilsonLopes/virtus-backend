package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.ComponentElementRequestDTO;
import com.virtus.domain.dto.request.ComponentGradeTypeRequestDTO;
import com.virtus.domain.dto.request.ComponentRequestDTO;
import com.virtus.domain.dto.response.ComponentResponseDTO;
import com.virtus.domain.entity.*;
import com.virtus.persistence.ComponentRepository;
import com.virtus.persistence.ElementComponentRepository;
import com.virtus.persistence.GradeTypeComponentRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ComponentService extends BaseService<Component, ComponentRepository, ComponentRequestDTO, ComponentResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final ElementComponentRepository elementComponentRepository;
    private final GradeTypeComponentRepository gradeTypeComponentRepository;

    public ComponentService(ComponentRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory,
                            ElementComponentRepository elementComponentRepository,
                            GradeTypeComponentRepository gradeTypeComponentRepository) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
        this.elementComponentRepository = elementComponentRepository;
        this.gradeTypeComponentRepository = gradeTypeComponentRepository;
    }

    @Override
    protected ComponentResponseDTO parseToResponseDTO(Component entity, boolean detailed) {
        return parseToComponentResponse(entity, detailed);
    }

    @Override
    protected Component parseToEntity(ComponentRequestDTO body) {
        Component component = body.getId() != null ? getRepository().findById(body.getId()).orElse(new Component()) : new Component();
        if (component.getAuthor() == null) component.setAuthor(getLoggedUser());
        component.setName(body.getName());
        component.setPga(body.isPga());
        component.setReference(body.getReference());
        component.setDescription(body.getDescription());
        component.getElementComponents().clear();
        component.getGradeTypeComponents().clear();
        component.getElementComponents().addAll(parseToComponentElements(body.getComponentElements(), component));
        component.getGradeTypeComponents().addAll(parseToComponentGradeTypes(body.getComponentGradeTypes(), component));
        return component;
    }

    private List<GradeTypeComponent> parseToComponentGradeTypes(List<ComponentGradeTypeRequestDTO> componentGradeTypes, Component component) {
        if (CollectionUtils.isEmpty(componentGradeTypes)) {
            return new ArrayList<>();
        }
        return componentGradeTypes.stream().map(cgt -> parseToComponentGradeType(cgt, component)).collect(Collectors.toList());
    }

    private GradeTypeComponent parseToComponentGradeType(ComponentGradeTypeRequestDTO dto, Component component) {
        GradeTypeComponent gradeTypeComponent = new GradeTypeComponent();
        gradeTypeComponent.setId(dto.getId());
        gradeTypeComponent.setGradeType(new GradeType(dto.getGradeType().getId()));
        gradeTypeComponent.setComponent(component);
        gradeTypeComponent.setStandardWeight(dto.getStandardWeight());
        return gradeTypeComponent;
    }

    private List<ElementComponent> parseToComponentElements(List<ComponentElementRequestDTO> componentElements, Component component) {
        if (CollectionUtils.isEmpty(componentElements)) {
            return new ArrayList<>();
        }
        return componentElements.stream().map(ce -> parseToComponentElement(ce, component)).collect(Collectors.toList());
    }

    private ElementComponent parseToComponentElement(ComponentElementRequestDTO dto, Component component) {
        ElementComponent elementComponent = new ElementComponent();
        elementComponent.setId(dto.getId());
        elementComponent.setComponent(component);
        elementComponent.setStandardWeight(dto.getStandardWeight());
        elementComponent.setGradeType(new GradeType(dto.getGradeType().getId()));
        elementComponent.setElement(new Element(dto.getElement().getId()));
        return elementComponent;
    }

    @Override
    protected void completeDetails(Component entity) {
        if (!CollectionUtils.isEmpty(entity.getElementComponents())) {
            AtomicReference<Integer> maxId = new AtomicReference<>(elementComponentRepository.findMaxId());
            entity.getElementComponents().forEach(elementComponent -> {
                if (elementComponent.getId() == null) {
                    elementComponent.setId(maxId.updateAndGet(v -> v + 1));
                }
            });
        }
        if (!CollectionUtils.isEmpty(entity.getGradeTypeComponents())) {
            AtomicReference<Integer> maxId = new AtomicReference<>(gradeTypeComponentRepository.findMaxId());
            entity.getGradeTypeComponents().forEach(gradeTypeComponent -> {
                if (gradeTypeComponent.getId() == null) {
                    gradeTypeComponent.setId(maxId.updateAndGet(v -> v + 1));
                }
            });
        }
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("component.not.found");
    }
}
