package com.virtus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.AutomaticScoreRequestDTO;
import com.virtus.domain.dto.response.AutomaticScoreResponseDTO;
import com.virtus.domain.entity.AutomaticScore;
import com.virtus.service.AutomaticScoreService;

@RestController
@RequestMapping("/automatic-scores")
public class AutomaticScoreController extends
        BaseController<AutomaticScore, AutomaticScoreService, AutomaticScoreRequestDTO, AutomaticScoreResponseDTO> {

    public AutomaticScoreController(AutomaticScoreService service) {
        super(service);
    }
}
