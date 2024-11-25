package com.fpt.niceshoes.service;

import com.fpt.niceshoes.dto.request.shoedetail.UpdateShoeDetailRequest;
import com.fpt.niceshoes.entity.ShoeDetail;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.shoedetail.ShoeDetailRequest;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.request.shoedetail.FindShoeDetailRequest;
import com.fpt.niceshoes.dto.response.ShoeDetailResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShoeDetailService {
    PageableObject<ShoeDetailResponse> getAll(FindShoeDetailRequest request);
    ShoeDetail getOne(Long id);
    String create(List<ShoeDetailRequest> list);
    ShoeDetail update(Long id, UpdateShoeDetailRequest request);
    ShoeDetail delete(Long id);

    ResponseObject updateFast(List<ShoeDetailRequest> list);
    Map<String, BigDecimal> findMinAndMaxPrice();
    ShoeDetailResponse getOneShoeDetail(Long id);
}
