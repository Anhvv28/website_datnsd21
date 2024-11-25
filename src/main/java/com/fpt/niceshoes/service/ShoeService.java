package com.fpt.niceshoes.service;

import com.fpt.niceshoes.dto.request.shoe.FindShoeReqeust;
import com.fpt.niceshoes.entity.Shoe;
import com.fpt.niceshoes.dto.request.shoe.ShoeRequest;
import com.fpt.niceshoes.dto.response.ShoeResponse;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.response.promotion.ShoePromotionResponse;

import java.util.List;

public interface ShoeService {
    PageableObject<ShoeResponse> getAll(FindShoeReqeust request);
    Shoe getOne(Long id);
    Shoe create(ShoeRequest request);
    Shoe update(Long id,ShoeRequest request);
    Shoe changeStatus(Long id);

    List<ShoePromotionResponse> getAllShoeInPromotion(Long promotion);
    List<ShoeResponse> getTopSell(Integer top);
}
