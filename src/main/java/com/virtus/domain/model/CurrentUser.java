package com.virtus.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CurrentUser {

    private Integer id;
    private String name;
    private String email;
    private String username;
    private LocalDateTime expiresIn;

}
