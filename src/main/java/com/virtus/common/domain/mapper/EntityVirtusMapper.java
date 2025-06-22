package com.virtus.common.domain.mapper;

import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.entity.EntityVirtus;

public class EntityVirtusMapper {

    public static EntityVirtusResponseDTO toResponseDTO(EntityVirtus entity) {
        if (entity == null) {
            return null;
        }

        EntityVirtusResponseDTO dto = new EntityVirtusResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAcronym(entity.getAcronym());
        dto.setCode(entity.getCode());
        return dto;
    }

}