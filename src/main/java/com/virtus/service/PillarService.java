package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.PillarComponentRequestDTO;
import com.virtus.domain.dto.request.PillarRequestDTO;
import com.virtus.domain.dto.response.PillarResponseDTO;
import com.virtus.domain.entity.Component;
import com.virtus.domain.entity.Pillar;
import com.virtus.domain.entity.ComponentPillar;
import com.virtus.persistence.PillarRepository;
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
public class PillarService extends BaseService<Pillar, PillarRepository, PillarRequestDTO, PillarResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    public PillarService(PillarRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    protected PillarResponseDTO parseToResponseDTO(Pillar entity, boolean detailed) {
        return parseToPillarResponseDTO(entity, detailed);
    }

    protected Pillar parseToEntity(PillarRequestDTO body) {
        Pillar entity = new Pillar();
        entity.setId(body.getId());
        entity.setName(body.getName());
        entity.setDescription(body.getDescription());
        entity.setReference(body.getReference());
        entity.setComponentPillars(parseToPillarComponents(body.getComponents(), entity));
        return entity;
    }

    private List<ComponentPillar> parseToPillarComponents(List<PillarComponentRequestDTO> list, Pillar pillar) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(dto -> parseToPillarComponent(dto, pillar)).collect(Collectors.toList());
    }

    private ComponentPillar parseToPillarComponent(PillarComponentRequestDTO dto, Pillar pillar) {
        ComponentPillar componentPillar = new ComponentPillar();
        componentPillar.setId(dto.getId());
        componentPillar.setAverageType(dto.getAverageType().getValue());
        componentPillar.setStandardWeight(dto.getStandardWeight());
        componentPillar.setProbeFile(dto.getProbeFile());
        componentPillar.setComponent(new Component(dto.getComponent().getId()));
        componentPillar.setAuthor(getLoggedUser());
        componentPillar.setPillar(pillar);
        return componentPillar;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("pillar.not.found");
    }
}
