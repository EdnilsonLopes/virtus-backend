package com.virtus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.CurrentGradesDTO;
import com.virtus.domain.dto.CurrentValuesDTO;
import com.virtus.domain.dto.CurrentWeightsDTO;
import com.virtus.domain.dto.request.ProductElementItemRequestDTO;
import com.virtus.domain.dto.request.ProductElementRequestDTO;
import com.virtus.domain.dto.request.ProductPillarRequestDTO;
import com.virtus.domain.dto.response.ComponentResponseDTO;
import com.virtus.domain.dto.response.CycleEntityResponseDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.dto.response.ElementItemResponseDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.EvaluatePlansTreeNode;
import com.virtus.domain.dto.response.GradeTypeResponseDTO;
import com.virtus.domain.dto.response.PillarResponseDTO;
import com.virtus.domain.dto.response.PlanResponseDTO;
import com.virtus.domain.entity.CycleEntity;
import com.virtus.domain.model.CurrentUser;
import com.virtus.domain.model.EvaluatePlansConsultModel;
import com.virtus.persistence.ComponentRepository;
import com.virtus.persistence.CycleEntityRepository;
import com.virtus.persistence.CycleRepository;
import com.virtus.persistence.ElementItemRepository;
import com.virtus.persistence.ElementRepository;
import com.virtus.persistence.EvaluatePlansRepository;
import com.virtus.persistence.GradeTypeRepository;
import com.virtus.persistence.OfficeRepository;
import com.virtus.persistence.PillarRepository;
import com.virtus.persistence.PlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluatePlansService {

        private final OfficeRepository officeRepository;
        private final CycleRepository cycleRepository;
        private final CycleEntityRepository cycleEntityRepository;
        private final ProductCycleService productCycleService;
        private final ProductPillarService productPillarService;
        private final ProductComponentService productComponentService;
        private final ProductPlanService productPlanService;
        private final ProductGradeTypeService productGradeTypeService;
        private final ProductElementService productElementService;
        private final ProductElementItemService productElementItemService;
        private final ProductElementHistoryService productElementHistoryService;
        private final ProductPillarHistoryService productPillarHistoryService;
        private final PillarRepository pillarRepository;
        private final ComponentRepository componentRepository;
        private final PlanRepository planRepository;
        private final GradeTypeRepository gradeTypeRepository;
        private final ElementRepository elementRepository;
        private final ElementItemRepository elementItemRepository;
        private final EvaluatePlansRepository evaluatePlansRepository;

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
                                                                .periodoPermitido(plan.getPeriodoPermitido())
                                                                .periodoCiclo(plan.getPeriodoCiclo())
                                                                .cicloAnalisado(plan.getCicloAnalisado())
                                                                .cicloDescrito(plan.getCicloDescrito())
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
                                                                .periodoCiclo(plan.getPeriodoCiclo())
                                                                .cicloAnalisado(plan.getCicloAnalisado())
                                                                .pilarAnalisado(plan.getPilarAnalisado())
                                                                .cicloDescrito(plan.getCicloDescrito())
                                                                .pilarDescrito(plan.getPilarDescrito())
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
                                                                .periodoCiclo(plan.getPeriodoCiclo())
                                                                .cicloAnalisado(plan.getCicloAnalisado())
                                                                .pilarAnalisado(plan.getPilarAnalisado())
                                                                .componenteAnalisado(plan.getComponenteAnalisado())
                                                                .cicloDescrito(plan.getCicloDescrito())
                                                                .pilarDescrito(plan.getPilarDescrito())
                                                                .componenteDescrito(plan.getComponenteDescrito())
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
                                                                .cicloAnalisado(plan.getCicloAnalisado())
                                                                .pilarAnalisado(plan.getPilarAnalisado())
                                                                .componenteAnalisado(plan.getComponenteAnalisado())
                                                                .planoAnalisado(plan.getPlanoAnalisado())
                                                                .cicloDescrito(plan.getCicloDescrito())
                                                                .pilarDescrito(plan.getPilarDescrito())
                                                                .componenteDescrito(plan.getComponenteDescrito())
                                                                .planoDescrito(plan.getPlanoDescrito())
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
                                                                .cicloAnalisado(plan.getCicloAnalisado())
                                                                .pilarAnalisado(plan.getPilarAnalisado())
                                                                .componenteAnalisado(plan.getComponenteAnalisado())
                                                                .planoAnalisado(plan.getPlanoAnalisado())
                                                                .tipoNotaAnalisado(plan.getTipoNotaAnalisado())
                                                                .cicloDescrito(plan.getCicloDescrito())
                                                                .pilarDescrito(plan.getPilarDescrito())
                                                                .componenteDescrito(plan.getComponenteDescrito())
                                                                .planoDescrito(plan.getPlanoDescrito())
                                                                .tipoNotaDescrito(plan.getTipoNotaDescrito())
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
                                                                .cicloAnalisado(plan.getCicloAnalisado())
                                                                .pilarAnalisado(plan.getPilarAnalisado())
                                                                .componenteAnalisado(plan.getComponenteAnalisado())
                                                                .planoAnalisado(plan.getPlanoAnalisado())
                                                                .tipoNotaAnalisado(plan.getTipoNotaAnalisado())
                                                                .elementoAnalisado(plan.getElementoAnalisado())
                                                                .cicloDescrito(plan.getCicloDescrito())
                                                                .pilarDescrito(plan.getPilarDescrito())
                                                                .componenteDescrito(plan.getComponenteDescrito())
                                                                .planoDescrito(plan.getPlanoDescrito())
                                                                .tipoNotaDescrito(plan.getTipoNotaDescrito())
                                                                .elementoDescrito(plan.getElementoDescrito())
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
                                                .cicloAnalisado(plan.getCicloAnalisado())
                                                .pilarAnalisado(plan.getPilarAnalisado())
                                                .componenteAnalisado(plan.getComponenteAnalisado())
                                                .planoAnalisado(plan.getPlanoAnalisado())
                                                .tipoNotaAnalisado(plan.getTipoNotaAnalisado())
                                                .elementoAnalisado(plan.getElementoAnalisado())
                                                .itemAnalisado(plan.getItemAnalisado())
                                                .cicloDescrito(plan.getCicloDescrito())
                                                .pilarDescrito(plan.getPilarDescrito())
                                                .componenteDescrito(plan.getComponenteDescrito())
                                                .planoDescrito(plan.getPlanoDescrito())
                                                .tipoNotaDescrito(plan.getTipoNotaDescrito())
                                                .elementoDescrito(plan.getElementoDescrito())
                                                .itemDescrito(plan.getItemDescrito())
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

        public PillarResponseDTO getPillarDescription(Integer id) {
                return pillarRepository.findById(id)
                                .map((com.virtus.domain.entity.Pillar p) -> PillarResponseDTO.builder()
                                                .id(p.getId())
                                                .name(p.getName())
                                                .description(p.getDescription())
                                                .build())
                                .orElse(null);
        }

        public ComponentResponseDTO getComponentDescription(Integer id) {
                return componentRepository.findById(id)
                                .map((com.virtus.domain.entity.Component c) -> ComponentResponseDTO.builder()
                                                .id(c.getId())
                                                .name(c.getName())
                                                .description(c.getDescription())
                                                .build())
                                .orElse(null);
        }

        public BaseResponseDTO getPlanDescription(Integer id) {
                return planRepository.findById(id)
                                .map((com.virtus.domain.entity.Plan c) -> PlanResponseDTO.builder()
                                                .id(c.getId())
                                                .name(c.getName())
                                                .description(c.getDescription())
                                                .build())
                                .orElse(null);
        }

        public BaseResponseDTO getCycleDescription(Integer id) {
                return cycleRepository.findById(id)
                                .map((com.virtus.domain.entity.Cycle c) -> CycleResponseDTO.builder()
                                                .id(c.getId())
                                                .name(c.getName())
                                                .description(c.getDescription())
                                                .build())
                                .orElse(null);
        }

        public BaseResponseDTO getGradeTypeDescription(Integer id) {
                return gradeTypeRepository.findById(id)
                                .map((com.virtus.domain.entity.GradeType gt) -> GradeTypeResponseDTO.builder()
                                                .id(gt.getId())
                                                .name(gt.getName())
                                                .description(gt.getDescription())
                                                .build())
                                .orElse(null);
        }

        public BaseResponseDTO getElementDescription(Integer id) {
                return elementRepository.findById(id)
                                .map((com.virtus.domain.entity.Element e) -> ElementItemResponseDTO.builder()
                                                .id(e.getId())
                                                .name(e.getName())
                                                .description(e.getDescription())
                                                .build())
                                .orElse(null);
        }

        public BaseResponseDTO getElementItemDescription(Integer id) {
                return elementItemRepository.findById(id)
                                .map((com.virtus.domain.entity.ElementItem ei) -> ElementItemResponseDTO.builder()
                                                .id(ei.getId())
                                                .name(ei.getName())
                                                .description(ei.getDescription())
                                                .build())
                                .orElse(null);
        }

        public void updateAnalysis(String objectType, ProductElementItemRequestDTO dto, CurrentUser currentUser) {
                evaluatePlansRepository.updateAnalysis(objectType, dto, currentUser);
        }

        public String getCycleAnalysys(Long entidadeId, Long cicloId) {
                return productCycleService.findByCycleLevelIds(entidadeId, cicloId);
        }

        public String getPillarAnalysys(Long entityId, Long cycleId, Long pillarId) {
                return productPillarService.findByCycleLevelIds(entityId, cycleId, pillarId);
        }

        public String getComponentAnalysys(Long entityId, Long cycleId, Long pillarId, Long componentId) {
                return productComponentService.findByCycleLevelIds(entityId, cycleId, pillarId, componentId);
        }

        public String getPlanAnalysys(Long entityId, Long cycleId, Long pillarId, Long componentId, Long planId) {
                return productPlanService.findByCycleLevelIds(entityId, cycleId, pillarId, componentId, planId);
        }

        public String getGradeTypeAnalysys(Long entityId, Long cycleId, Long pillarId, Long componentId, Long planId,
                        Long gradeTypeId) {
                return productGradeTypeService.findByCycleLevelIds(entityId, cycleId, pillarId, componentId, planId,
                                gradeTypeId);
        }

        public String getElementAnalysys(Long entityId, Long cycleId, Long pillarId, Long componentId, Long planId,
                        Long gradeTypeId, Long elementId) {
                return productElementService.findByCycleLevelIds(entityId, cycleId, pillarId, componentId, planId,
                                gradeTypeId, elementId);
        }

        public String getElementItemAnalysys(Long entityId, Long cycleId, Long pillarId, Long componentId, Long planId,
                        Long gradeTypeId, Long elementId, Long itemId) {
                return productElementItemService.findByCycleLevelIds(entityId, cycleId, pillarId, componentId, planId,
                                gradeTypeId, elementId, itemId);
        }

}
