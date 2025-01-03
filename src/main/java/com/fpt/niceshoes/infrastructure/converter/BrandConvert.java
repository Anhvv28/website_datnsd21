package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.entity.Brand;
import com.fpt.niceshoes.dto.request.properties.BrandRequest;
import org.springframework.stereotype.Component;

@Component
public class BrandConvert {
    public Brand convertRequestToEntity(BrandRequest request) {
        Brand brand = Brand.builder()
                .name(request.getName())
                .build();
        return brand;
    }

    public Brand convertRequestToEntity(Brand entity, BrandRequest request) {
        entity.setName(request.getName());
        return entity;
    }
}
