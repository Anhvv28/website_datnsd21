package com.fpt.niceshoes.service;

import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.PaymentMethodRequest;
import com.fpt.niceshoes.dto.response.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodResponse> getByBill(Long idBill);
    ResponseObject create(PaymentMethodRequest request);
}
