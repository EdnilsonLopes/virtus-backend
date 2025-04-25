package com.virtus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.virtus.common.BaseController;
import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.EntityVirtusRequestDTO;
import com.virtus.domain.dto.response.CycleEntityResponseDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.EntityVirtusService;

@Controller
@RequestMapping("/entities")
public class EntityVirtusController extends BaseController<EntityVirtus, EntityVirtusService, EntityVirtusRequestDTO, EntityVirtusResponseDTO> {

    public EntityVirtusController(EntityVirtusService service) {
        super(service);
    }

    @GetMapping("/cycle-entity/by-entity-id")
    public ResponseEntity<List<CycleEntityResponseDTO>> getCyclesEntitiesByEntityId(@LoggedUser CurrentUser currentUser, @RequestParam Integer entityId) {
        return ResponseEntity.ok(getService().findCyclesEntitiesByEntityId(currentUser, entityId));
    }

}
