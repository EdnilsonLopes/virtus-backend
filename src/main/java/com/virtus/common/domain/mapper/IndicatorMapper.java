package com.virtus.common.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.virtus.domain.dto.request.IndicatorRequestDTO;
import com.virtus.domain.dto.response.IndicatorResponseDTO;
import com.virtus.domain.entity.Indicator;

public class IndicatorMapper {

    public static Indicator fromRequestDTO(IndicatorRequestDTO dto) {
        Indicator indicator = new Indicator();
        indicator.setIndicatorAcronym(dto.getIndicatorAcronym());
        indicator.setIndicatorName(dto.getIndicatorName());
        indicator.setIndicatorDescription(dto.getIndicatorDescription());
        return indicator;
    }

    public static IndicatorResponseDTO toResponseDTO(Indicator indicator) {
        IndicatorResponseDTO dto = new IndicatorResponseDTO();
        dto.setId(indicator.getId());
        dto.setIndicatorAcronym(indicator.getIndicatorAcronym());
        dto.setIndicatorName(indicator.getIndicatorName());
        dto.setIndicatorDescription(indicator.getIndicatorDescription());
        dto.setAuthorName(indicator.getAuthor() != null ? indicator.getAuthor().getName() : null);
        dto.setCreatedAt(indicator.getCreatedAt());
        return dto;
    }

    public static List<IndicatorResponseDTO> toDTOList(List<Indicator> entities) {
        return entities.stream()
                .map(IndicatorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}