package com.virtus.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ActivitiesByProductComponentRequestDto {
    private Integer productComponentId;
    private Integer supervisorId;
    private Integer auditorId;
    private LocalDate startsAt;
    private LocalDate endsAt;
}
