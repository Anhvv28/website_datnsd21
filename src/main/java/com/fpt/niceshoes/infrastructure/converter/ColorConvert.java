package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.entity.Color;
import com.fpt.niceshoes.dto.request.properties.ColorRequest;
import org.springframework.stereotype.Component;

@Component
public class ColorConvert {
    public Color convertRequestToEntity(ColorRequest request){
        Color size = Color.builder()
                .name(request.getName())
                .build();
        return size;
    }

    public Color convertRequestToEntity(Color entity, ColorRequest request){
        entity.setName(request.getName());
        return entity;
    }
}
