package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.GradeTypeRequestDTO;
import com.virtus.domain.dto.response.GradeTypeResponseDTO;
import com.virtus.domain.entity.GradeType;
import com.virtus.service.GradeTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grade-types")
public class GradeTypeController extends BaseController<
        GradeType,
        GradeTypeService,
        GradeTypeRequestDTO,
        GradeTypeResponseDTO> {

    public GradeTypeController(GradeTypeService service) {
        super(service);
    }
}
