package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.CycleRequestDTO;
import com.virtus.domain.dto.request.StartCycleRequestDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.entity.Cycle;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.CycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cycles")
public class CycleController extends BaseController<Cycle, CycleService, CycleRequestDTO, CycleResponseDTO> {

    public CycleController(CycleService service) {
        super(service);
    }

    @PostMapping("/start-cycle")
    public ResponseEntity<Void> startCycle(@LoggedUser CurrentUser currentUser, @RequestBody StartCycleRequestDTO body){
        getService().startCycle(currentUser, body);
        return ResponseEntity.ok().build();
    }
}
