package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.FeatureRequestDTO;
import com.virtus.domain.dto.response.FeatureResponseDTO;
import com.virtus.domain.entity.Feature;
import com.virtus.service.FeatureService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/features")
public class FeatureController extends BaseController<Feature, FeatureService, FeatureRequestDTO, FeatureResponseDTO> {

    public FeatureController(FeatureService service) {
        super(service);
    }
}
