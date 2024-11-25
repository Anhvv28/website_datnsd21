package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.entity.Size;
import com.fpt.niceshoes.dto.request.properties.SizeRequest;
import org.springframework.stereotype.Component;

@Component
public class SizeConvert {
    public Size convertRequestToEntity(SizeRequest request){
        Size size = Size.builder()
                .name(request.getName())
                .build();
        return size;
    }

    public Size convertRequestToEntity(Size entity, SizeRequest request){
        entity.setName(request.getName());
        return entity;
    }
}
