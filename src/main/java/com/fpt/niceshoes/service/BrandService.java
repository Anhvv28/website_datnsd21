package com.fpt.niceshoes.service;

import com.fpt.niceshoes.entity.Brand;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.request.properties.BrandRequest;
import com.fpt.niceshoes.dto.response.BrandResponse;

public interface BrandService {
    PageableObject<BrandResponse> getAll(BrandRequest request);

    Brand getOne(Long id);

    Brand create(BrandRequest request);

    Brand update(Long id, BrandRequest request);

    Brand delete(Long id);
}
