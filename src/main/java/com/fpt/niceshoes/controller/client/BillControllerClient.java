package com.fpt.niceshoes.controller.client;


import com.fpt.niceshoes.dto.request.bill.BillRequest;
import com.fpt.niceshoes.dto.request.bill.BillSearchRequest;
import com.fpt.niceshoes.dto.request.billdetail.BillClientRequest;
import com.fpt.niceshoes.dto.response.statistic.StatisticBillStatus;
import com.fpt.niceshoes.entity.Bill;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.service.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/api/bill")
public class BillControllerClient {
    @Autowired
    private BillService billService;

    @GetMapping
    public PageableObject getAll(BillSearchRequest request) {
        return billService.getAll(request);
    }

    @GetMapping("/find-by-code")
    public Bill findByCode(@RequestParam(required = false, defaultValue = "") String code) {
        return billService.findByCode(code);
    }

    @GetMapping("/statistic-bill-status")
    public List<StatisticBillStatus> statisticBillStatus() {
        return billService.statisticBillStatus();
    }

    @GetMapping("/{id}")
    public Bill getOne(@PathVariable Long id) {
        return billService.getOne(id);
    }

    @PostMapping
    public ResponseObject create() {
        return new ResponseObject(billService.create());
    }

    @PostMapping("/create-bill-client")
    public ResponseObject create(@RequestBody BillClientRequest request) {
        return new ResponseObject(billService.createBillClient(request));
    }

    @PostMapping("/create-bill-client-vn-pay/{code}")
    public ResponseObject createVnPay(@RequestBody BillClientRequest request,
                                      @PathVariable("code") String code) {
        return new ResponseObject(billService.createBillClientVnpay(request, code));
    }

    @PutMapping("/{id}")
    public ResponseObject orderBill(@PathVariable Long id, @RequestBody @Valid BillRequest request) {
        return new ResponseObject(billService.orderBill(id, request));
    }

    @GetMapping("/change-status/{id}")
    public ResponseObject changeStatus(@PathVariable Long id, @RequestParam String note, @RequestParam(defaultValue = "false") Boolean isCancel) {
        return new ResponseObject(billService.changeStatus(id, note, isCancel));
    }
}
