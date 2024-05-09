package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributeActivitiesTreeResponseDTO {

    private EntityVirtusResponseDTO entity;
    private List<CycleResponseDTO> cycles;

}
