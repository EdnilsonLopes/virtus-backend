package com.virtus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.virtus.domain.dto.CurrentGradesDTO;
import com.virtus.domain.dto.CurrentValuesDTO;
import com.virtus.domain.dto.CurrentWeightsDTO;
import com.virtus.domain.dto.request.ProductElementRequestDTO;
import com.virtus.domain.dto.request.ProductPillarRequestDTO;
import com.virtus.domain.dto.response.CycleEntityResponseDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.EvaluatePlansTreeNode;
import com.virtus.domain.entity.CycleEntity;
import com.virtus.domain.model.CurrentUser;
import com.virtus.domain.model.EvaluatePlansConsultModel;
import com.virtus.persistence.CycleEntityRepository;
import com.virtus.persistence.EvaluatePlansRepository;
import com.virtus.persistence.OfficeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluatePlansService {

        private final OfficeRepository officeRepository;
        private final CycleEntityRepository cycleEntityRepository;
        private final EvaluatePlansRepository evaluatePlansRepository;
        private final ProductElementHistoryService productElementHistoryService;
        private final ProductPillarHistoryService productPillarHistoryService;

        public List<EntityVirtusResponseDTO> listEvaluatePlans(CurrentUser currentUser, String filter) {
                var entidades = officeRepository.listEvaluatePlans(currentUser.getId(), filter);

                if (CollectionUtils.isEmpty(entidades)) {
                        return List.of();
                }
                List<EntityVirtusResponseDTO> responseDTOS = new ArrayList<>();
                for (Object[] entidade : entidades) {
                        List<CycleEntity> cycleEntity = cycleEntityRepository
                                        .listEntitiesCycles(Integer.parseInt(entidade[1].toString()));
                        responseDTOS.add(buildEntityResponse(entidade, cycleEntity));
                }

                return responseDTOS;

        }

        private EntityVirtusResponseDTO buildEntityResponse(Object[] entidade, List<CycleEntity> ciclosEntidades) {
                return EntityVirtusResponseDTO.builder()
                                .code(String.valueOf(entidade[0]))
                                .id(Integer.parseInt(String.valueOf(entidade[1])))
                                .name(String.valueOf(entidade[2]))
                                .acronym(String.valueOf(entidade[3]))
                                .cyclesEntity(ciclosEntidades.stream().map(this::parseCycleEntityResponse)
                                                .collect(Collectors.toList()))
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

        public List<EvaluatePlansTreeNode> findEvaluatePlansTree(CurrentUser currentUser, Integer entityId,
                        Integer cycleId) {
                List<EvaluatePlansConsultModel> result = evaluatePlansRepository.findPlansByEntityAndCycle(entityId,
                                cycleId);
                return buildEvaluatePlansTree(result);
        }

        public List<EvaluatePlansTreeNode> buildEvaluatePlansTree(List<EvaluatePlansConsultModel> plans) {
                Map<Integer, EvaluatePlansTreeNode> entidadeMap = new HashMap<>();

                for (EvaluatePlansConsultModel plan : plans) {
                        // Nível 1: Entidade
                        entidadeMap.putIfAbsent(plan.getEntidadeId(),
                                        EvaluatePlansTreeNode.builder()
                                                        .id(plan.getEntidadeId())
                                                        .name(plan.getEntidadeNome())
                                                        .type("Entidade")
                                                        .children(new ArrayList<>())
                                                        .build());
                        EvaluatePlansTreeNode entidadeNode = entidadeMap.get(plan.getEntidadeId());

                        // Nível 2: Ciclo
                        EvaluatePlansTreeNode cicloNode = entidadeNode.getChildren().stream()
                                        .filter(node -> node.getId().equals(plan.getCicloId()))
                                        .findFirst()
                                        .orElseGet(() -> {
                                                EvaluatePlansTreeNode node = EvaluatePlansTreeNode.builder()
                                                                .id(plan.getCicloId())
                                                                .name(plan.getCicloNome())
                                                                .type("Ciclo")
                                                                .grade(plan.getCicloNota())
                                                                .letter(plan.getTipoNotaLetra())
                                                                .children(new ArrayList<>())
                                                                .build();
                                                entidadeNode.addChild(node);
                                                return node;
                                        });

                        // Nível 3: Pilar
                        EvaluatePlansTreeNode pilarNode = cicloNode.getChildren().stream()
                                        .filter(node -> node.getId().equals(plan.getPilarId()))
                                        .findFirst()
                                        .orElseGet(() -> {
                                                EvaluatePlansTreeNode node = EvaluatePlansTreeNode.builder()
                                                                .id(plan.getPilarId())
                                                                .name(plan.getPilarNome())
                                                                .type("Pilar")
                                                                .grade(plan.getPilarNota())
                                                                .weight(plan.getPilarPeso())
                                                                .periodoPermitido(plan.getPeriodoPermitido())
                                                                .supervisorId(plan.getSupervisorId())
                                                                .children(new ArrayList<>())
                                                                .build();
                                                cicloNode.addChild(node);
                                                return node;
                                        });

                        // Nível 4: Componente
                        EvaluatePlansTreeNode componenteNode = pilarNode.getChildren().stream()
                                        .filter(node -> node.getId().equals(plan.getComponenteId()))
                                        .findFirst()
                                        .orElseGet(() -> {
                                                EvaluatePlansTreeNode node = EvaluatePlansTreeNode.builder()
                                                                .id(plan.getComponenteId())
                                                                .name(plan.getComponenteNome())
                                                                .type("Componente")
                                                                .grade(plan.getComponenteNota())
                                                                .weight(plan.getComponentePeso())
                                                                .auditorId(plan.getAuditorId())
                                                                .supervisorId(plan.getSupervisorId())
                                                                .children(new ArrayList<>())
                                                                .build();
                                                pilarNode.addChild(node);
                                                return node;
                                        });

                        // Nível 5: Plano
                        EvaluatePlansTreeNode planoNode = componenteNode.getChildren().stream()
                                        .filter(node -> node.getId().equals(plan.getPlanoId()))
                                        .findFirst()
                                        .orElseGet(() -> {
                                                EvaluatePlansTreeNode node = EvaluatePlansTreeNode.builder()
                                                                .id(plan.getPlanoId())
                                                                .name("Plano " + plan.getPlanoId())
                                                                .type("Plano")
                                                                .grade(plan.getPlanoNota())
                                                                .weight(plan.getPlanoPeso())
                                                                .children(new ArrayList<>())
                                                                .build();
                                                componenteNode.addChild(node);
                                                return node;
                                        });

                        // Nível 6: Tipo Nota
                        EvaluatePlansTreeNode tipoNotaNode = planoNode.getChildren().stream()
                                        .filter(node -> node.getId().equals(plan.getTipoNotaId()))
                                        .findFirst()
                                        .orElseGet(() -> {
                                                EvaluatePlansTreeNode node = EvaluatePlansTreeNode.builder()
                                                                .id(plan.getTipoNotaId())
                                                                .name(plan.getTipoNotaNome())
                                                                .type("Tipo Nota")
                                                                .grade(plan.getTipoNotaNota())
                                                                .weight(plan.getTipoNotaPeso())
                                                                .children(new ArrayList<>())
                                                                .build();
                                                planoNode.addChild(node);
                                                return node;
                                        });

                        // Nível 7: Elemento
                        EvaluatePlansTreeNode elementoNode = tipoNotaNode.getChildren().stream()
                                        .filter(node -> node.getId().equals(plan.getElementoId()))
                                        .findFirst()
                                        .orElseGet(() -> {
                                                EvaluatePlansTreeNode node = EvaluatePlansTreeNode.builder()
                                                                .id(plan.getElementoId())
                                                                .name(plan.getElementoNome())
                                                                .type("Elemento")
                                                                .grade(plan.getElementoNota())
                                                                .weight(plan.getElementoPeso())
                                                                .auditorId(plan.getAuditorId())
                                                                .supervisorId(plan.getSupervisorId())
                                                                .periodoPermitido(plan.getPeriodoPermitido())
                                                                .gradeTypeId(plan.getTipoPontuacaoId())
                                                                .children(new ArrayList<>())
                                                                .build();
                                                tipoNotaNode.addChild(node);
                                                return node;
                                        });

                        // Nível 8: Item (se o item tiver um ID válido)
                        if (plan.getItemId() != 0) {
                                EvaluatePlansTreeNode itemNode = EvaluatePlansTreeNode.builder()
                                                .id(plan.getItemId())
                                                .name(plan.getItemNome())
                                                .type("Item")
                                                .children(new ArrayList<>())
                                                .build();
                                elementoNode.addChild(itemNode);
                        }
                }

                return new ArrayList<>(entidadeMap.values());
        }

        public CurrentValuesDTO updateElementGrade(ProductElementRequestDTO dto, CurrentUser currentUser) {
                evaluatePlansRepository.updateElementGrade(dto, currentUser);
                productElementHistoryService.recordElementGradeHistory(dto, currentUser);
                evaluatePlansRepository.updateGradeTypeWeights(dto, currentUser);
                evaluatePlansRepository.updatePlanWeights(dto, currentUser);
                evaluatePlansRepository.updateComponentWeights(dto, currentUser);
                // Current Weights
                CurrentWeightsDTO currentWeights = evaluatePlansRepository.loadCurrentWeights(dto);

                // Recalculate Grades
                evaluatePlansRepository.updateGradeTypeGrade(dto);
                evaluatePlansRepository.updatePlanGrade(dto);
                evaluatePlansRepository.updateComponentGrade(dto);
                evaluatePlansRepository.updatePillarGrade(dto);
                evaluatePlansRepository.updateCycleGrade(dto);

                // Current Grades
                CurrentGradesDTO currentGrades = evaluatePlansRepository.loadCurrentGrades(dto);
                // Return merged updated data
                return buildCurrentValues(currentWeights, currentGrades);
        }

        // Build Current Values
        private CurrentValuesDTO buildCurrentValues(CurrentWeightsDTO ps, CurrentGradesDTO ns) {
                return CurrentValuesDTO.builder()
                                .cicloPeso(ps.getCicloPeso())
                                .pilarPeso(ps.getPilarPeso())
                                .componentePeso(ps.getComponentePeso())
                                .planoPeso(ps.getPlanoPeso())
                                .tipoNotaPeso(ps.getTipoNotaPeso())
                                .elementoPeso(ps.getElementoPeso())
                                .tipoNotaNota(ns.getTipoNotaNota())
                                .planoNota(ns.getPlanoNota())
                                .componenteNota(ns.getComponenteNota())
                                .pilarNota(ns.getPilarNota())
                                .cicloNota(ns.getCicloNota())
                                .build();
        }

        public CurrentValuesDTO updateElementWeight(ProductElementRequestDTO dto, CurrentUser currentUser) {
                evaluatePlansRepository.updateElementWeight(dto, currentUser);
                productElementHistoryService.recordElementWeightHistory(dto, currentUser);
                evaluatePlansRepository.updateGradeTypeWeights(dto, currentUser);
                evaluatePlansRepository.updatePlanWeights(dto, currentUser);
                evaluatePlansRepository.updateComponentWeights(dto, currentUser);
                // Current Weights
                CurrentWeightsDTO currentWeights = evaluatePlansRepository.loadCurrentWeights(dto);
                currentWeights.setElementoPeso(String.valueOf(dto.getPeso()));
                // Recalculate Grades
                evaluatePlansRepository.updateGradeTypeGrade(dto);
                evaluatePlansRepository.updatePlanGrade(dto);
                evaluatePlansRepository.updateComponentGrade(dto);
                evaluatePlansRepository.updatePillarGrade(dto);
                evaluatePlansRepository.updateCycleGrade(dto);

                // Current Grades
                CurrentGradesDTO currentGrades = evaluatePlansRepository.loadCurrentGrades(dto);
                return buildCurrentValues(currentWeights, currentGrades);
        }

        public CurrentValuesDTO updatePillarWeight(ProductPillarRequestDTO dto, CurrentUser currentUser) {
                String cicloNota = evaluatePlansRepository.updatePillarWeight(dto, currentUser);
                productPillarHistoryService.recordPillarWeight(dto, currentUser);
                return CurrentValuesDTO.builder().cicloNota(cicloNota).build();
        }
}
