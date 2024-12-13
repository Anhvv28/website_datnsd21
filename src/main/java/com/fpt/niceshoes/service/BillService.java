package com.fpt.niceshoes.service;

import com.fpt.niceshoes.dto.request.giveback.GivebackRequest;
import com.fpt.niceshoes.dto.response.statistic.StatisticBillStatus;
import com.fpt.niceshoes.entity.Bill;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.billdetail.BillClientRequest;
import com.fpt.niceshoes.dto.request.bill.BillRequest;
import com.fpt.niceshoes.dto.request.bill.BillSearchRequest;
import com.fpt.niceshoes.dto.response.BillResponse;

import java.util.List;

public interface BillService {
    PageableObject<BillResponse> getAll(BillSearchRequest request);

    List<Bill> getNewBill(BillSearchRequest request);

    Bill getOne(Long id);

    Bill findByCode(String code);

    Bill create();

    Bill orderBill(Long id, BillRequest request);

    Bill updateBill();

    ResponseObject createBillClient(BillClientRequest request);

    ResponseObject createBillClientVnpay(BillClientRequest request, String code);

    Bill delete(Long id);

    Bill changeStatus(Long id, String status, Boolean isCancel);

    Bill changeInfoCustomer(Long id, BillRequest request);

    List<StatisticBillStatus> statisticBillStatus();

    ResponseObject givebackAll(Long idBill, String note);
    ResponseObject giveback(GivebackRequest request);
}
