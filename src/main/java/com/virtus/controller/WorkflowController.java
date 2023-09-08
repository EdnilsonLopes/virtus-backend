package com.virtus.controller;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.WorkflowRequestDTO;
import com.virtus.domain.dto.response.WorkflowResponseDTO;
import com.virtus.domain.entity.Workflow;
import com.virtus.service.WorkflowService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflows")
public class WorkflowController extends BaseController<Workflow, WorkflowService, WorkflowRequestDTO, WorkflowResponseDTO> {

    public WorkflowController(WorkflowService service) {
        super(service);
    }
}
