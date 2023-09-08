package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.OfficeRequestDTO;
import com.virtus.domain.dto.response.OfficeResponseDTO;
import com.virtus.domain.entity.Office;
import com.virtus.service.OfficeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offices")
public class OfficeController extends BaseController<Office, OfficeService, OfficeRequestDTO, OfficeResponseDTO> {

    public OfficeController(OfficeService service) {
        super(service);
    }
}
