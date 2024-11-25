package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.entity.BillHistory;
import com.fpt.niceshoes.dto.request.BillHistoryRequest;
import com.fpt.niceshoes.repository.IBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillHistoryConvert {
    @Autowired
    private IBillRepository billRepository;

    public BillHistory convertRequestToEntity(BillHistoryRequest request) {
        return BillHistory.builder()
                .bill(billRepository.findById(request.getIdBill()).get())
                .note(request.getNote())
                .status(request.getStatus())
                .build();
    }
}
