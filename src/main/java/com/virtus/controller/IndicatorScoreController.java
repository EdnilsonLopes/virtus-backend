package com.virtus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.IndicatorScoreRequestDTO;
import com.virtus.domain.dto.response.IndicatorScoreResponseDTO;
import com.virtus.domain.entity.IndicatorScore;
import com.virtus.service.IndicatorScoreService;

@RestController
@RequestMapping("/indicator-scores")
public class IndicatorScoreController extends
                BaseController<IndicatorScore, IndicatorScoreService, IndicatorScoreRequestDTO, IndicatorScoreResponseDTO> {

        public IndicatorScoreController(IndicatorScoreService service) {
                super(service);
        }

}
