package com.virtus.controller;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.response.DistributeActivitiesResponseDTO;
import com.virtus.domain.dto.response.DistributeActivitiesTreeResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.DistributeActivitiesService;
import com.virtus.service.JurisdictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/by-entity-id")
    public ResponseEntity<List<DistributeActivitiesTreeResponseDTO>> getDistributeActivitiesTree(@LoggedUser CurrentUser currentUser, @RequestParam Integer entityId) {
        return ResponseEntity.ok(service.findDistributeActivitiesTree(currentUser, entityId));
    }

}
