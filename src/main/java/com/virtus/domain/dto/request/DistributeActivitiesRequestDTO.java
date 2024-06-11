package com.virtus.domain.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DistributeActivitiesRequestDTO {

    private List<ActivitiesByProductComponentRequestDto> activities;

}
