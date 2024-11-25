package com.fpt.niceshoes.service;

import com.fpt.niceshoes.entity.BillDetail;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.request.billdetail.BillDetailRequest;
import com.fpt.niceshoes.dto.response.BillDetailResponse;

import java.math.BigDecimal;

public interface BillDetailService {
    PageableObject<BillDetailResponse> getAll(BillDetailRequest request);

    BillDetail getOne(Long id);
    BillDetail create(BillDetailRequest request);
    BillDetail update(Long id,BillDetailRequest request);
    BillDetail delete(Long id);

    BillDetail updateQuantity(Long id, Integer newQuantity, BigDecimal price);
}
