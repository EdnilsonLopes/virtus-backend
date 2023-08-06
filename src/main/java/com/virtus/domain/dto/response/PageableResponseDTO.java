package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PageableResponseDTO<DTO extends BaseResponseDTO> {

    private List<DTO> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;


}
