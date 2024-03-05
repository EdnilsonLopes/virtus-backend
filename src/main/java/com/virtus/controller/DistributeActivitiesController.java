package com.virtus.controller;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.response.JurisdictionResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.JurisdictionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/distribute-activities")
public class DistributeActivitiesController {

    private final JurisdictionService jurisdictionService;

    public DistributeActivitiesController(JurisdictionService jurisdictionService) {
        this.jurisdictionService = jurisdictionService;
    }

    @GetMapping()
    public ResponseEntity<PageableResponseDTO<JurisdictionResponseDTO>> getJurisdictionsByCurrentUser(@LoggedUser CurrentUser currentUser,
                                                                                         @RequestParam(required = false, defaultValue = "") String filter,
                                                                                         @RequestParam(required = false, defaultValue = "0") int page,
                                                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(jurisdictionService.findAll(currentUser, page, size));
    }

}
