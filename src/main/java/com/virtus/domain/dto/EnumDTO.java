package com.virtus.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnumDTO<E extends Enum> {

    private E value;
    private String description;

}
