package com.fpt.niceshoes.service;

import com.fpt.niceshoes.entity.Size;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.request.properties.SizeRequest;
import com.fpt.niceshoes.dto.response.SizeResponse;

public interface SizeService {
    PageableObject<SizeResponse> getAll(SizeRequest request);

    Size getOne(Long id);

    Size create(SizeRequest request);

    Size update(Long id, SizeRequest request);

    Size delete(Long id);
}
