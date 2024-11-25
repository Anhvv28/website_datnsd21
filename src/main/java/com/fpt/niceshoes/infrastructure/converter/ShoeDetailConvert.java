package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.entity.Color;
import com.fpt.niceshoes.entity.Shoe;
import com.fpt.niceshoes.entity.ShoeDetail;
import com.fpt.niceshoes.entity.Size;
import com.fpt.niceshoes.entity.Sole;
import com.fpt.niceshoes.infrastructure.common.GenCode;
import com.fpt.niceshoes.dto.request.shoedetail.ShoeDetailRequest;
import com.fpt.niceshoes.repository.IColorRepository;
import com.fpt.niceshoes.repository.IShoeRepository;
import com.fpt.niceshoes.repository.ISizeRepository;
import com.fpt.niceshoes.repository.ISoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoeDetailConvert {
    @Autowired
    private IShoeRepository shoeRepository;
    @Autowired
    private IColorRepository colorRepository;
    @Autowired
    private ISizeRepository sizeRepository;
    @Autowired
    private ISoleRepository soleRepository;

    public ShoeDetail convertRequestToEntity(ShoeDetailRequest request) {
        Shoe shoe = shoeRepository.findById(request.getShoe()).get();
        Color color = colorRepository.findById(request.getColor()).get();
        Size size = sizeRepository.findById(request.getSize()).get();
        Sole sole = soleRepository.findById(request.getSole()).get();
        return ShoeDetail.builder()
                .shoe(shoe).color(color).size(size).sole(sole)
                .code(GenCode.genCodeByName(shoe.getName()
                        + color.getName() + size.getName() + sole.getName()))
                .price(request.getPrice()).quantity(request.getQuantity())
                .weight(request.getWeight())
                .build();
    }

    public ShoeDetail convertRequestToEntity(ShoeDetail entity, ShoeDetailRequest request) {
        Shoe shoe = shoeRepository.findById(request.getShoe()).get();
        Color color = colorRepository.findById(request.getColor()).get();
        Size size = sizeRepository.findById(request.getSize()).get();
        Sole sole = soleRepository.findById(request.getSole()).get();

        entity.setShoe(shoe);
        entity.setColor(color);
        entity.setSize(size);
        entity.setCode(GenCode.genCodeByName(shoe.getName()
                + color.getName() + size.getName() + sole.getName()));
        entity.setPrice(request.getPrice());
        entity.setQuantity(request.getQuantity());
        entity.setWeight(request.getWeight());
        return entity;
    }

    public ShoeDetail convertRequestToEntityFast(ShoeDetail entity, ShoeDetailRequest request) {
        entity.setPrice(request.getPrice());
        entity.setQuantity(request.getQuantity());
        entity.setWeight(request.getWeight());
        return entity;
    }
}
