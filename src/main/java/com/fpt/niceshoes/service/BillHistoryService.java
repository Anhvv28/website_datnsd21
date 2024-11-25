package com.fpt.niceshoes.service;

import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.BillHistoryRequest;
import com.fpt.niceshoes.dto.response.BillHistoryResponse;

import java.util.List;

public interface BillHistoryService {
    List<BillHistoryResponse> getByBill(Long idBill);
    ResponseObject create(BillHistoryRequest request);
}
