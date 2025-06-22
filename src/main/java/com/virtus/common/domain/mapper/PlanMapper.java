package com.virtus.common.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.virtus.domain.dto.response.PlanResponseDTO;
import com.virtus.domain.entity.Plan;

public class PlanMapper {

    public static List<PlanResponseDTO> toResponseDTO(List<Plan> plans) {
        return plans.stream().map(plan -> {
            PlanResponseDTO dto = new PlanResponseDTO();
            dto.setId(plan.getId());
            dto.setEntity(EntityVirtusMapper.toResponseDTO(plan.getEntity()));
            dto.setReference(null != plan.getReference() ? plan.getReference() : null);
            dto.setName(null != plan.getName() ? plan.getName() : null);
            dto.setDescription(null != plan.getDescription() ? plan.getDescription() : null);
            dto.setCnpb(null != plan.getCnpb() ? plan.getCnpb() : null);
            dto.setLegislation(null != plan.getLegislation() ? plan.getLegislation() : null);
            dto.setSituation(null != plan.getSituation() ? plan.getSituation() : null);
            dto.setModality(null != plan.getModality() ? plan.getModality() : null);
            dto.setGuaranteeResource(null != plan.getGuaranteeResource() ? plan.getGuaranteeResource() : null);
            return dto;
        }).collect(Collectors.toList());
    }

    public static PlanResponseDTO toResponseDTO(Plan plan) {
        if (plan == null) {
            return null;
        }
        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setId(plan.getId());
        dto.setEntity(EntityVirtusMapper.toResponseDTO(plan.getEntity()));
        dto.setReference(null != plan.getReference() ? plan.getReference() : null);
        dto.setName(null != plan.getName() ? plan.getName() : null);
        dto.setDescription(null != plan.getDescription() ? plan.getDescription() : null);
        dto.setCnpb(null != plan.getCnpb() ? plan.getCnpb() : null);
        dto.setLegislation(null != plan.getLegislation() ? plan.getLegislation() : null);
        dto.setSituation(null != plan.getSituation() ? plan.getSituation() : null);
        dto.setModality(null != plan.getModality() ? plan.getModality() : null);
        dto.setGuaranteeResource(null != plan.getGuaranteeResource() ? plan.getGuaranteeResource() : null);
        return dto;
    }

}