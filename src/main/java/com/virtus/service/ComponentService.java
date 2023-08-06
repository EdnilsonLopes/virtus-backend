package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.ComponentElementRequestDTO;
import com.virtus.domain.dto.request.ComponentGradeTypeRequestDTO;
import com.virtus.domain.dto.request.ComponentRequestDTO;
import com.virtus.domain.dto.response.ComponentResponseDTO;
import com.virtus.domain.entity.*;
import com.virtus.persistence.ComponentRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComponentService extends BaseService<Component, ComponentRepository, ComponentRequestDTO, ComponentResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    public ComponentService(ComponentRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    protected ComponentResponseDTO parseToResponseDTO(Component entity, boolean detailed) {
        return parseToComponentResponse(entity, detailed);
    }

    @Override
    protected Component parseToEntity(ComponentRequestDTO body) {
        Component component = new Component();
        component.setId(body.getId());
        component.setName(body.getName());
        component.setPga(body.isPga());
        component.setReference(body.getReference());
        component.setDescription(body.getDescription());

        component.setElementComponents(parseToComponentElements(body.getComponentElements(), component));
        component.setGradeTypeComponents(parseToComponentGradeTypes(body.getComponentGradeTypes(), component));
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
    protected String getNotFoundMessage() {
        return Translator.translate("component.not.found");
    }
}
