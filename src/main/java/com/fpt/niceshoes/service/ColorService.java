package com.fpt.niceshoes.service;

import com.fpt.niceshoes.entity.Color;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.request.properties.ColorRequest;
import com.fpt.niceshoes.dto.response.ColorResponse;

public interface ColorService {
    PageableObject<ColorResponse> getAll(ColorRequest request);

    Color getOne(Long id);

    Color create(ColorRequest request);

    Color update(Long id, ColorRequest request);

    Color delete(Long id);
}
