package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.PillarRequestDTO;
import com.virtus.domain.dto.response.PillarResponseDTO;
import com.virtus.domain.entity.Pillar;
import com.virtus.service.PillarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pillars")
public class PillarController extends BaseController<Pillar, PillarService, PillarRequestDTO, PillarResponseDTO> {

    public PillarController(PillarService service) {
        super(service);
    }
}
