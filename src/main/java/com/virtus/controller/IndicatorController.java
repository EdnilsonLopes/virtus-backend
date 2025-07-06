package com.virtus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.IndicatorRequestDTO;
import com.virtus.domain.dto.response.IndicatorResponseDTO;
import com.virtus.domain.entity.Indicator;
import com.virtus.service.IndicatorService;

@RestController
@RequestMapping("/indicators")
public class IndicatorController  extends BaseController<
        Indicator,
        IndicatorService,
        IndicatorRequestDTO,
        IndicatorResponseDTO> {

    public  IndicatorController( IndicatorService service) {
        super(service);
    }
}
