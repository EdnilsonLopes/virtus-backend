package com.virtus.common.domain.mapper;

import com.virtus.domain.dto.response.IndicatorScoreResponseDTO;
import com.virtus.domain.entity.IndicatorScore;

import java.util.List;
import java.util.stream.Collectors;

public class IndicatorScoreMapper {

    public static IndicatorScoreResponseDTO toResponseDTO(IndicatorScore entity) {
        if (entity == null) return null;

        return IndicatorScoreResponseDTO.builder()
                .id(entity.getId())
                .cnpb(entity.getCnpb())
                .referenceDate(entity.getReferenceDate())
                .indicatorId(entity.getIndicatorId())
                .indicatorSigla(entity.getIndicatorSigla())
                .componentText(entity.getComponentText())
                .score(entity.getScore())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static List<IndicatorScoreResponseDTO> toResponseDTOList(List<IndicatorScore> entities) {
        return entities.stream()
                .map(IndicatorScoreMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
