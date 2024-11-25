package com.fpt.niceshoes.service;

import com.fpt.niceshoes.entity.Sole;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.request.properties.SoleRequest;
import com.fpt.niceshoes.dto.response.SoleResponse;

public interface SoleService {
    PageableObject<SoleResponse> getAll(SoleRequest request);

    Sole getOne(Long id);

    Sole create(SoleRequest request);

    Sole update(Long id, SoleRequest request);

    Sole delete(Long id);
}
