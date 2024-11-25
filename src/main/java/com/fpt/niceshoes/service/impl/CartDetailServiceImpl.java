package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.dto.request.CartClientRequest;
import com.fpt.niceshoes.entity.CartDetail;
import com.fpt.niceshoes.repository.ICartDetailRepository;
import com.fpt.niceshoes.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private ICartDetailRepository cartDetailRepository;

    @Override
    public Boolean deleteCartDetail(Long id) {
        cartDetailRepository.deleteById(id);
        return true;
    }

    @Override
    public String changeQuantity(CartClientRequest cartClientRequest) {
        CartDetail cartDetail = cartDetailRepository.findById(cartClientRequest.getId()).get();
        cartDetail.setQuantity(cartDetail.getQuantity());
        cartDetailRepository.save(cartDetail);
        return "ok";
    }
}
