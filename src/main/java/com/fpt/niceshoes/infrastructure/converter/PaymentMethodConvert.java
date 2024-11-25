package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.entity.PaymentMethod;
import com.fpt.niceshoes.dto.request.PaymentMethodRequest;
import com.fpt.niceshoes.repository.IBillRepository;
import com.fpt.niceshoes.repository.IPaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodConvert {
    @Autowired
    private IPaymentMethodRepository repository;
    @Autowired
    private IBillRepository billRepository;
    public PaymentMethod convertRequestToEntity(PaymentMethodRequest request){
        return PaymentMethod.builder()
                .method(request.getMethod())
                .totalMoney(request.getTotalMoney())
                .note(request.getNote())
                .tradingCode(request.getTradingCode())
                .bill(billRepository.findById(request.getBill()).orElse(null))
                .type(request.getType())
                .build();
    }
}
