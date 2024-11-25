package com.fpt.niceshoes.controller.admin;

import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.PaymentMethodRequest;
import com.fpt.niceshoes.dto.response.PaymentMethodResponse;
import com.fpt.niceshoes.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment-method")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService service;
    @GetMapping("/{id}")
    public List<PaymentMethodResponse> getAll(@PathVariable Long id){
        return service.getByBill(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody PaymentMethodRequest request){
        return service.create(request);
    }
}
