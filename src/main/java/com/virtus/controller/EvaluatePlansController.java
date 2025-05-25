package com.virtus.controller;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.CurrentValuesDTO;
import com.virtus.domain.dto.request.ProductElementRequestDTO;
import com.virtus.domain.dto.request.ProductPillarRequestDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.EvaluatePlansTreeNode;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.EvaluatePlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/evaluate-plans")
@RequiredArgsConstructor
public class EvaluatePlansController {

    private final EvaluatePlansService service;

    @GetMapping("/list")
    public ResponseEntity<List<EntityVirtusResponseDTO>> listAvaliarPlanos(@LoggedUser CurrentUser currentUser, @RequestParam(value = "filter", required = false) String filter) {
        return ResponseEntity.ok(service.listEvaluatePlans(currentUser, filter));
    }

    @GetMapping("/by-entity-and-cycle-id")
    public ResponseEntity<List<EvaluatePlansTreeNode>> getEvaluatePlansTree(@LoggedUser CurrentUser currentUser, @RequestParam Integer entityId, @RequestParam Integer cycleId) {
        List<EvaluatePlansTreeNode> evaluatePlansTree = service.findEvaluatePlansTree(currentUser, entityId, cycleId);
        return ResponseEntity.ok(evaluatePlansTree);
    }

    @PutMapping("/updateElementGrade")
    public ResponseEntity<CurrentValuesDTO> updateElementGrade(@RequestBody ProductElementRequestDTO dto, CurrentUser currentUser) {
        CurrentValuesDTO valoresAtuais = service.updateElementGrade(dto, currentUser);
        return ResponseEntity.ok(valoresAtuais);
    }

    @PutMapping("/updatePillarWeight")
    public ResponseEntity<CurrentValuesDTO> updatePillarWeight(@RequestBody ProductPillarRequestDTO dto, CurrentUser currentUser) {
        CurrentValuesDTO valoresAtuais = service.updatePillarWeight(dto, currentUser);
        return ResponseEntity.ok(valoresAtuais);
    }

}
