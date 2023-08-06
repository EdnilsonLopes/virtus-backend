package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.CycleRequestDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.entity.Cycle;
import com.virtus.service.CycleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cycles")
public class CycleController extends BaseController<Cycle, CycleService, CycleRequestDTO, CycleResponseDTO> {

    public CycleController(CycleService service) {
        super(service);
    }
}
