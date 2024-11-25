package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.dto.request.PromotionRequest;
import com.fpt.niceshoes.entity.Promotion;
import com.fpt.niceshoes.repository.IPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionConvert {
    @Autowired
    private IPromotionRepository promotionRepository;

    public Promotion convertRequestToEntity(PromotionRequest request){
        return Promotion.builder()
                .code(request.getCode())
                .name(request.getName())
                .value(request.getValue())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .build();
    }

    public Promotion convertRequestToEntity(Promotion promotion, PromotionRequest request){
        promotion.setCode(request.getCode());
        promotion.setName(request.getName());
        promotion.setValue(request.getValue());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
        promotion.setStatus(request.getStatus());
        return promotion;
    }
}
