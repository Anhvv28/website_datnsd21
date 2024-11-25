package com.fpt.niceshoes.controller.admin;

import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.BillHistoryRequest;
import com.fpt.niceshoes.dto.response.BillHistoryResponse;
import com.fpt.niceshoes.service.BillHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bill-history")
public class BillHistoryController {
    @Autowired
    private BillHistoryService billHistoryService;
    @GetMapping("/{idBill}")
    public List<BillHistoryResponse> getByBill(@PathVariable("idBill") Long id){
        return billHistoryService.getByBill(id);
    }
    @PostMapping
    public ResponseObject create(@RequestBody BillHistoryRequest request){
        return billHistoryService.create(request);
    }
}
