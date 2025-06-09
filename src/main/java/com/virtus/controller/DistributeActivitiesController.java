package com.virtus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.ActivitiesByProductComponentRequestDto;
import com.virtus.domain.dto.request.ProductComponentRequestDTO;
import com.virtus.domain.dto.request.UpdateConfigPlanRequestDTO;
import com.virtus.domain.dto.response.DistributeActivitiesResponseDTO;
import com.virtus.domain.dto.response.DistributeActivitiesTreeResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.dto.response.PlanResponseDTO;
import com.virtus.domain.dto.response.ProductPlanResponseDTO;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.DistributeActivitiesService;
import com.virtus.service.JurisdictionService;

@RestController
@RequestMapping("/distribute-activities")
public class DistributeActivitiesController {

    private final DistributeActivitiesService service;
    private final JurisdictionService jurisdictionService;


    public DistributeActivitiesController(DistributeActivitiesService service, JurisdictionService jurisdictionService) {
        this.service = service;
        this.jurisdictionService = jurisdictionService;
    }

    @GetMapping()
    public ResponseEntity<PageableResponseDTO<DistributeActivitiesResponseDTO>> getJurisdictionsByCurrentUser(@LoggedUser CurrentUser currentUser,
                                                                                                              @RequestParam(required = false, defaultValue = "") String filter,
                                                                                                              @RequestParam(required = false, defaultValue = "0") int page,
                                                                                                              @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(jurisdictionService.findEntidadeCiclosByUser(currentUser, page, size));
    }

    @PostMapping
    public ResponseEntity<Void> distributeActivities(
            @LoggedUser CurrentUser currentUser,
            @RequestBody List<ActivitiesByProductComponentRequestDto> body
    ) {
        service.distributeActivities(currentUser, body);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-entity-and-cycle-id")
    public ResponseEntity<DistributeActivitiesTreeResponseDTO> getDistributeActivitiesTree(@LoggedUser CurrentUser currentUser, @RequestParam Integer entityId, @RequestParam Integer cycleId) {
        return ResponseEntity.ok(service.findDistributeActivitiesTree(currentUser, entityId, cycleId));
    }

    @GetMapping("/config-plans")
    public ResponseEntity<List<PlanResponseDTO>> listConfigPlans(
            @LoggedUser CurrentUser currentUser,
            @RequestParam Integer entityId,
            @RequestParam Integer cycleId,
            @RequestParam Integer pillarId,
            @RequestParam boolean pga){
        return ResponseEntity.ok(service.listPlansToConfig(currentUser, entityId, cycleId, pillarId, pga));
    }

    @GetMapping("/configured-plans")
    public ResponseEntity<List<ProductPlanResponseDTO>> listConfiguredPlans(
            @LoggedUser CurrentUser currentUser,
            @RequestParam Integer entityId,
            @RequestParam Integer cycleId,
            @RequestParam Integer pillarId,
            @RequestParam Integer componentId){
        return ResponseEntity.ok(service.listConfiguredPlans( entityId, cycleId, pillarId, componentId));
    }

    @PostMapping("/config-plans")
    public ResponseEntity<Void> listConfigPlans(
            @LoggedUser CurrentUser currentUser,
            @RequestBody UpdateConfigPlanRequestDTO body){
        service.updateConfigPlans(currentUser, body);
        return ResponseEntity.ok().build();
    }

    
    @PostMapping("/udpateNewAuditorComponent")
    public ResponseEntity<Void> updateNewAuditorComponent(
            @LoggedUser CurrentUser currentUser,
            @RequestBody ProductComponentRequestDTO body){
        service.updateNewAuditorComponent(currentUser, body);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/updateReschedullingComponent")
    public ResponseEntity<Void> updateReschedullingComponent(
            @LoggedUser CurrentUser currentUser,
            @RequestBody ProductComponentRequestDTO body){
        service.updateReschedullingComponent(currentUser, body);
        return ResponseEntity.ok().build();
    }
}
