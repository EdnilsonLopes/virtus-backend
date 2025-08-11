package com.virtus.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.CurrentValuesDTO;
import com.virtus.domain.dto.request.ProductElementItemRequestDTO;
import com.virtus.domain.dto.request.ProductElementRequestDTO;
import com.virtus.domain.dto.request.ProductPillarRequestDTO;
import com.virtus.domain.dto.request.ProductPlanRequestDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.EvaluatePlansTreeNode;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.EvaluatePlansService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/evaluate-plans")
@RequiredArgsConstructor
public class EvaluatePlansController {

    private final EvaluatePlansService service;

    @GetMapping("/list")
    public ResponseEntity<List<EntityVirtusResponseDTO>> listAvaliarPlanos(@LoggedUser CurrentUser currentUser,
            @RequestParam(value = "filter", required = false) String filter) {
        return ResponseEntity.ok(service.listEvaluatePlans(currentUser, filter));
    }

    @GetMapping("/by-entity-and-cycle-id")
    public ResponseEntity<List<EvaluatePlansTreeNode>> getEvaluatePlansTree(@LoggedUser CurrentUser currentUser,
            @RequestParam Integer entityId, @RequestParam Integer cycleId) {
        List<EvaluatePlansTreeNode> evaluatePlansTree = service.findEvaluatePlansTree(currentUser, entityId, cycleId);
        return ResponseEntity.ok(evaluatePlansTree);
    }

    @PutMapping("/updateElementGrade")
    public ResponseEntity<CurrentValuesDTO> updateElementGrade(@LoggedUser CurrentUser currentUser,
            @RequestBody ProductElementRequestDTO dto) {
        CurrentValuesDTO valoresAtuais = service.updateElementGrade(dto, currentUser);
        return ResponseEntity.ok(valoresAtuais);
    }

    @PutMapping("/updateElementWeight")
    public ResponseEntity<CurrentValuesDTO> updateElementWeight(@LoggedUser CurrentUser currentUser,
            @RequestBody ProductElementRequestDTO dto) {
        CurrentValuesDTO valoresAtuais = service.updateElementWeight(dto, currentUser);
        return ResponseEntity.ok(valoresAtuais);
    }

    @PutMapping("/updatePillarWeight")
    public ResponseEntity<CurrentValuesDTO> updatePillarWeight(@LoggedUser CurrentUser currentUser,
            @RequestBody ProductPillarRequestDTO dto) {
        CurrentValuesDTO valoresAtuais = service.updatePillarWeight(dto, currentUser);
        return ResponseEntity.ok(valoresAtuais);
    }

    @GetMapping("/description")
    public ResponseEntity<BaseResponseDTO> getDescription(
            @LoggedUser CurrentUser currentUser,
            @RequestParam("id") Integer id,
            @RequestParam("objectType") String objectType) {

        BaseResponseDTO dto;

        switch (objectType) {
            case "Ciclo":
                dto = service.getCycleDescription(id);
                break;
            case "Pilar":
                dto = service.getPillarDescription(id);
                break;
            case "Componente":
                dto = service.getComponentDescription(id);
                break;
            case "Plano":
                dto = service.getPlanDescription(id);
                break;
            case "Tipo Nota":
                dto = service.getGradeTypeDescription(id);
                break;
            case "Elemento":
                dto = service.getElementDescription(id);
                break;
            case "Item":
                dto = service.getElementItemDescription(id);
                break;
            default:
                return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/analysis")
    public ResponseEntity<Map<String, String>> getAnalysis(
            @LoggedUser CurrentUser currentUser,
            @RequestParam("objectType") String objectType,
            @RequestParam Long entityId,
            @RequestParam Long cycleId, 
            @RequestParam Long pillarId,
            @RequestParam Long componentId,
            @RequestParam Long planId,
            @RequestParam Long gradeTypeId,
            @RequestParam Long elementId,
            @RequestParam Long itemId) {

        String analysis = "";

        switch (objectType) {
            case "Ciclo":
                analysis = service.getCycleAnalysys(entityId, cycleId);
                break;
            case "Pilar":
                analysis = service.getPillarAnalysys(entityId, cycleId, pillarId);
                break;
            case "Componente":
                analysis = service.getComponentAnalysys(entityId, cycleId, pillarId, componentId);
                break;
            case "Plano":
                analysis = service.getPlanAnalysys(entityId, cycleId, pillarId, componentId, planId);
                break;
            case "Tipo Nota":
                analysis = service.getGradeTypeAnalysys(entityId, cycleId, pillarId, componentId, planId, gradeTypeId);
                break;
            case "Elemento":
                analysis = service.getElementAnalysys(entityId, cycleId, pillarId, componentId, planId, gradeTypeId, elementId);
                break;
            case "Item":
                analysis = service.getElementItemAnalysys(entityId, cycleId, pillarId, componentId, planId, gradeTypeId, elementId, itemId);
                break;
            default:
                return ResponseEntity.badRequest().body(java.util.Collections.emptyMap());
        }
        return ResponseEntity.ok(java.util.Collections.singletonMap("analysis", analysis));
    }

    @PutMapping("/updateAnalysis")
    public ResponseEntity<CurrentValuesDTO> updateAnalysis(
            @LoggedUser CurrentUser currentUser,
            @RequestBody ProductElementItemRequestDTO dto, 
            @RequestParam("objectType") String objectType) {
        service.updateAnalysis(objectType, dto, currentUser);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/ratifyNote")
    public ResponseEntity<CurrentValuesDTO> ratifyNote(
            @LoggedUser CurrentUser currentUser,
            @RequestBody ProductElementRequestDTO requestDTO) {

        // Chama o serviço para ratificar a nota
        CurrentValuesDTO valoresAtuais = service.ratifyNote(requestDTO, currentUser);
        return ResponseEntity.ok(valoresAtuais);
    }

    // Método para retificar a nota
    @PutMapping("/rectifyNote")
    public ResponseEntity<CurrentValuesDTO> rectifyNote(
            @LoggedUser CurrentUser currentUser,
            @RequestBody ProductElementRequestDTO requestDTO) {

        // Chama o serviço para retificar a nota
        CurrentValuesDTO valoresAtuais = service.rectifyNote(requestDTO, currentUser);
        return ResponseEntity.ok(valoresAtuais);
    }

}
