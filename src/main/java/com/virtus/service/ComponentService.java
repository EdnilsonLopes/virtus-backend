package com.virtus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.ComponentElementRequestDTO;
import com.virtus.domain.dto.request.ComponentGradeTypeRequestDTO;
import com.virtus.domain.dto.request.ComponentIndicatorRequestDTO;
import com.virtus.domain.dto.request.ComponentRequestDTO;
import com.virtus.domain.dto.response.ComponentResponseDTO;
import com.virtus.domain.entity.Component;
import com.virtus.domain.entity.Element;
import com.virtus.domain.entity.ElementComponent;
import com.virtus.domain.entity.GradeType;
import com.virtus.domain.entity.GradeTypeComponent;
import com.virtus.domain.entity.Indicator;
import com.virtus.domain.entity.IndicatorComponent;
import com.virtus.persistence.ComponentRepository;
import com.virtus.persistence.ElementComponentRepository;
import com.virtus.persistence.GradeTypeComponentRepository;
import com.virtus.persistence.IndicatorComponentRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;

@Service
public class ComponentService
        extends BaseService<Component, ComponentRepository, ComponentRequestDTO, ComponentResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final ElementComponentRepository elementComponentRepository;
    private final GradeTypeComponentRepository gradeTypeComponentRepository;
    private final IndicatorComponentRepository indicatorComponentRepository;

    public ComponentService(ComponentRepository repository,
            UserRepository userRepository,
            EntityManagerFactory entityManagerFactory,
            ElementComponentRepository elementComponentRepository,
            GradeTypeComponentRepository gradeTypeComponentRepository,
            IndicatorComponentRepository indicatorComponentRepository) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
        this.elementComponentRepository = elementComponentRepository;
        this.gradeTypeComponentRepository = gradeTypeComponentRepository;
        this.indicatorComponentRepository = indicatorComponentRepository;
    }

    @Override
    protected ComponentResponseDTO parseToResponseDTO(Component entity, boolean detailed) {
        return parseToComponentResponse(entity, detailed);
    }

    @Override
    protected Component parseToEntity(ComponentRequestDTO body) {
        Component component = body.getId() != null
                ? getRepository().findById(body.getId()).orElse(new Component())
                : new Component();

        if (component.getAuthor() == null) {
            component.setAuthor(getLoggedUser());
        }

        component.setName(body.getName());
        component.setPga(body.isPga());
        component.setReference(body.getReference());
        component.setDescription(body.getDescription());

        // Recarrega listas de forma controlada
        component.getElementComponents().clear();
        component.getElementComponents().addAll(parseToComponentElements(body.getComponentElements(), component));

        component.getGradeTypeComponents().clear();
        component.getGradeTypeComponents().addAll(parseToComponentGradeTypes(body.getComponentGradeTypes(), component));

        component.getIndicatorComponents().clear();
        component.getIndicatorComponents().addAll(parseToComponentIndicators(body.getComponentIndicators(), component));

        return component;
    }

    private List<ElementComponent> parseToComponentElements(List<ComponentElementRequestDTO> dtos,
            Component component) {
        if (CollectionUtils.isEmpty(dtos))
            return new ArrayList<>();

        return dtos.stream().map(dto -> {
            ElementComponent entity = dto.getId() != null
                    ? elementComponentRepository.findById(dto.getId()).orElse(new ElementComponent())
                    : new ElementComponent();

            entity.setId(dto.getId());
            entity.setComponent(component);
            entity.setElement(new Element(dto.getElement().getId()));
            entity.setGradeType(new GradeType(dto.getGradeType().getId()));
            entity.setStandardWeight(dto.getStandardWeight());

            return entity;
        }).collect(Collectors.toList());
    }

    private List<GradeTypeComponent> parseToComponentGradeTypes(List<ComponentGradeTypeRequestDTO> dtos,
            Component component) {
        if (CollectionUtils.isEmpty(dtos))
            return new ArrayList<>();

        return dtos.stream().map(dto -> {
            GradeTypeComponent entity = dto.getId() != null
                    ? gradeTypeComponentRepository.findById(dto.getId()).orElse(new GradeTypeComponent())
                    : new GradeTypeComponent();

            entity.setId(dto.getId());
            entity.setComponent(component);
            entity.setGradeType(new GradeType(dto.getGradeType().getId()));
            entity.setStandardWeight(dto.getStandardWeight());

            return entity;
        }).collect(Collectors.toList());
    }

    private List<IndicatorComponent> parseToComponentIndicators(List<ComponentIndicatorRequestDTO> dtos,
            Component component) {
        if (CollectionUtils.isEmpty(dtos))
            return new ArrayList<>();

        return dtos.stream().map(dto -> {
            IndicatorComponent entity = dto.getId() != null
                    ? indicatorComponentRepository.findById(dto.getId()).orElse(new IndicatorComponent())
                    : new IndicatorComponent();

            entity.setId(dto.getId());
            entity.setComponent(component);
            entity.setIndicator(new Indicator(dto.getIndicator().getId()));
            entity.setStandardWeight(dto.getStandardWeight());

            return entity;
        }).collect(Collectors.toList());
    }

    @Override
    protected void completeDetails(Component entity) {
        if (!CollectionUtils.isEmpty(entity.getElementComponents())) {
            AtomicReference<Integer> maxId = new AtomicReference<>(elementComponentRepository.findMaxId());
            entity.getElementComponents().forEach(item -> {
                if (item.getId() == null) {
                    item.setId(maxId.updateAndGet(v -> v + 1));
                }
            });
        }

        if (!CollectionUtils.isEmpty(entity.getGradeTypeComponents())) {
            AtomicReference<Integer> maxId = new AtomicReference<>(gradeTypeComponentRepository.findMaxId());
            entity.getGradeTypeComponents().forEach(item -> {
                if (item.getId() == null) {
                    item.setId(maxId.updateAndGet(v -> v + 1));
                }
            });
        }

        if (!CollectionUtils.isEmpty(entity.getIndicatorComponents())) {
            AtomicReference<Integer> maxId = new AtomicReference<>(indicatorComponentRepository.findMaxId());
            entity.getIndicatorComponents().forEach(item -> {
                if (item.getId() == null) {
                    item.setId(maxId.updateAndGet(v -> v + 1));
                }
            });
        }
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("component.not.found");
    }
}
