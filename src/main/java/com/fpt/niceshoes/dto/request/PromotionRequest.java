package com.fpt.niceshoes.dto.request;

import com.fpt.niceshoes.infrastructure.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PromotionRequest extends PageableRequest {
    private Long id;
    private String code;
    private String name;
    private Float value;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer status;
    private List<Long> productDetails;
}
