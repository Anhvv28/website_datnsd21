package com.fpt.niceshoes.service;

import com.fpt.niceshoes.dto.request.CartClientRequest;
import com.fpt.niceshoes.dto.response.CartResponse;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;

import java.util.List;

public interface CartService {
    List<CartResponse> getListCart(Long idAccount);
    ResponseObject create(CartClientRequest request);
    ResponseObject updateQuantity(CartClientRequest request);
    ResponseObject deleteById(Long idCartDetail);
    ResponseObject deleteAll(Long idAccount);
}
