package com.virtus.controller;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.ProdutoElementoRequestDTO;
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
        return ResponseEntity.ok(service.listAvaliarPlanos(currentUser, filter));
    }

    @GetMapping("/by-entity-and-cycle-id")
    public ResponseEntity<List<EvaluatePlansTreeNode>> getEvaluatePlansTree(@LoggedUser CurrentUser currentUser, @RequestParam Integer entityId, @RequestParam Integer cycleId) {
        List<EvaluatePlansTreeNode> evaluatePlansTree = service.findEvaluatePlansTree(currentUser, entityId, cycleId);
        return ResponseEntity.ok(evaluatePlansTree);
    }

    @PutMapping("/salvarNotaElemento")
    public ResponseEntity<Void> salvarNotaElemento(@RequestBody ProdutoElementoRequestDTO dto, CurrentUser currentUser) {
        service.salvarNotaElemento(dto, currentUser);
        return ResponseEntity.ok().build();
    }

}
