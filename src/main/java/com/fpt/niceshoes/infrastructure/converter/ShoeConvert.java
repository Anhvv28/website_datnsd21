package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.entity.Shoe;
import com.fpt.niceshoes.dto.request.shoe.ShoeRequest;
import com.fpt.niceshoes.repository.IBrandRepository;
import com.fpt.niceshoes.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShoeConvert {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IBrandRepository brandRepository;
    public Shoe convertRequestToEntity(ShoeRequest request){
        Shoe shoe = Shoe.builder()
                .name(request.getName())
                .category(categoryRepository.findById(request.getCategory()).get())
                .brand(brandRepository.findById(request.getBrand()).get())
                .description(request.getDescription())
                .build();
        return shoe;
    }
    public Shoe convertRequestToEntity(Shoe entity, ShoeRequest request){
        entity.setName(request.getName());
        entity.setCategory(categoryRepository.findById(request.getCategory()).get());
        entity.setBrand(brandRepository.findById(request.getBrand()).get());
        entity.setDescription(request.getDescription());
        return entity;
    }
}
