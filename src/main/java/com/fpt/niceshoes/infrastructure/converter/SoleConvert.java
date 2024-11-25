package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.entity.Sole;
import com.fpt.niceshoes.dto.request.properties.SoleRequest;
import org.springframework.stereotype.Component;

@Component
public class SoleConvert {
    public Sole convertRequestToEntity(SoleRequest request){
        Sole size = Sole.builder()
                .name(request.getName())
                .build();
        return size;
    }

    public Sole convertRequestToEntity(Sole entity, SoleRequest request){
        entity.setName(request.getName());
        return entity;
    }
}
