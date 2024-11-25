package com.fpt.niceshoes.service;

import com.fpt.niceshoes.dto.request.CartClientRequest;

public interface CartDetailService {
    Boolean deleteCartDetail(Long id);

    String changeQuantity(CartClientRequest cartClientRequest);
}
