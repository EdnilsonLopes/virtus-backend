package com.virtus.controller;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.EvaluatePlansTreeResponseDTO;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.EvaluatePlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/evaluate-plans")
@RequiredArgsConstructor
public class EvaluatePlansController {

    private final EvaluatePlansService service;

    @GetMapping("/list")
    public ResponseEntity<List<EntityVirtusResponseDTO>> listAvaliarPlanos(@LoggedUser CurrentUser currentUser) {
        return ResponseEntity.ok(service.listAvaliarPlanos(currentUser));
    }

    @GetMapping("/by-entity-and-cycle-id")
    public ResponseEntity<List<EvaluatePlansTreeResponseDTO>> getDistributeActivitiesTree(@LoggedUser CurrentUser currentUser, @RequestParam Integer entityId, @RequestParam Integer cycleId) {
        return ResponseEntity.ok(service.findEvaluatePlansTree(currentUser, entityId, cycleId));
    }

}
