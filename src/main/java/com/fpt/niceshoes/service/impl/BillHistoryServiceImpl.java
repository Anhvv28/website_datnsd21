package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.infrastructure.converter.BillHistoryConvert;
import com.fpt.niceshoes.dto.request.BillHistoryRequest;
import com.fpt.niceshoes.dto.response.BillHistoryResponse;
import com.fpt.niceshoes.repository.IBillHistoryRepository;
import com.fpt.niceshoes.service.BillHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillHistoryServiceImpl implements BillHistoryService {
    @Autowired
    private IBillHistoryRepository repository;
    @Autowired
    private BillHistoryConvert billHistoryConvert;

    @Override
    public List<BillHistoryResponse> getByBill(Long idBill) {
        return repository.getByBill(idBill);
    }

    @Override
    public ResponseObject create(BillHistoryRequest request) {
        return new ResponseObject(billHistoryConvert.convertRequestToEntity(request));
    }
}
