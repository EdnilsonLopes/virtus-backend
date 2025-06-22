package com.virtus.controller;

import com.virtus.common.domain.mapper.PlanMapper;
import com.virtus.domain.dto.response.PlanResponseDTO;
import com.virtus.domain.entity.Plan;
import com.virtus.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    /**
     * Retorna todos os planos de uma entidade pelo ID.
     *
     * Exemplo: GET /plans/entity/123
     */
    @GetMapping("/by-entity/{entityId}")
    public List<PlanResponseDTO> findByEntityId(@PathVariable Integer entityId) {
        List<PlanResponseDTO> responseDTO = PlanMapper.toResponseDTO(planService.findByEntityId(entityId));
        return responseDTO;
    }

    @GetMapping("/cnpb/{cnpb}")
    public ResponseEntity<PlanResponseDTO> findByCnpb(@PathVariable String cnpb) {
        PlanResponseDTO dto = PlanMapper.toResponseDTO(planService.findByCnpb(cnpb));
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
