package com.virtus.service;

import com.virtus.domain.dto.response.CycleEntityResponseDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.EvaluatePlansTreeResponseDTO;
import com.virtus.domain.entity.CycleEntity;
import com.virtus.domain.model.CurrentUser;
import com.virtus.persistence.CycleEntityRepository;
import com.virtus.persistence.EvaluatePlansRepository;
import com.virtus.persistence.OfficeRepository;
import com.virtus.persistence.ProductPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluatePlansService {

    private final OfficeRepository officeRepository;
    private final CycleEntityRepository cycleEntityRepository;
    private final EvaluatePlansRepository evaluatePlansRepository;


    public List<EntityVirtusResponseDTO> listAvaliarPlanos(CurrentUser currentUser) {
        var entidades = officeRepository.listAvaliarPlanos(currentUser.getId());

        if (CollectionUtils.isEmpty(entidades)) {
            return List.of();
        }
        List<EntityVirtusResponseDTO> responseDTOS = new ArrayList<>();
        for (Object[] entidade : entidades) {
            List<CycleEntity> cycleEntity = cycleEntityRepository.listEntidadesCiclos(Integer.parseInt(entidade[1].toString()));
            responseDTOS.add(montarEntidadeResponse(entidade, cycleEntity));
        }

        return responseDTOS;

    }

    private EntityVirtusResponseDTO montarEntidadeResponse(Object[] entidade, List<CycleEntity> ciclosEntidades) {
        return EntityVirtusResponseDTO.builder()
                .code(String.valueOf(entidade[0]))
                .id(Integer.parseInt(String.valueOf(entidade[1])))
                .name(String.valueOf(entidade[2]))
                .acronym(String.valueOf(entidade[3]))
                .cyclesEntity(ciclosEntidades.stream().map(this::parseCycleEntityResponse).collect(Collectors.toList()))
                .build();
    }

    private CycleEntityResponseDTO parseCycleEntityResponse(CycleEntity cycleEntity) {
        return CycleEntityResponseDTO.builder()
                .cycle(CycleResponseDTO.builder()
                        .id(cycleEntity.getCycle().getId())
                        .name(cycleEntity.getCycle().getName())
                        .build())
                .build();
    }

    public List<EvaluatePlansTreeResponseDTO> findEvaluatePlansTree(CurrentUser currentUser, Integer entityId, Integer cycleId) {
        return evaluatePlansRepository.findPlansByEntityAndCycle(entityId, cycleId);
    }
}
