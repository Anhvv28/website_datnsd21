package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.entity.Bill;
import com.fpt.niceshoes.entity.BillHistory;
import com.fpt.niceshoes.entity.PaymentMethod;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.infrastructure.constant.BillStatusConstant;
import com.fpt.niceshoes.infrastructure.constant.PaymentMethodConstant;
import com.fpt.niceshoes.infrastructure.converter.PaymentMethodConvert;
import com.fpt.niceshoes.dto.request.PaymentMethodRequest;
import com.fpt.niceshoes.dto.response.PaymentMethodResponse;
import com.fpt.niceshoes.repository.IBillHistoryRepository;
import com.fpt.niceshoes.repository.IBillRepository;
import com.fpt.niceshoes.repository.IPaymentMethodRepository;
import com.fpt.niceshoes.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    private IPaymentMethodRepository repository;
    @Autowired
    private PaymentMethodConvert paymentMethodConvert;
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private IBillHistoryRepository billHistoryRepository;

    @Override
    public List<PaymentMethodResponse> getByBill(Long idBill) {
        return repository.getByBill(idBill);
    }

    @Override
    public ResponseObject create(PaymentMethodRequest request) {
        PaymentMethod paymentMethod = repository.save(paymentMethodConvert.convertRequestToEntity(request));
        List<PaymentMethod> paymentMethods = repository.findByBillIdAndType(request.getBill(), request.getType());
        Bill bill = paymentMethod.getBill();
        Double totalPayment = 0.0;
        for (PaymentMethod x: paymentMethods) {
            totalPayment+=x.getTotalMoney().doubleValue();
        }
        if (BigDecimal.valueOf(totalPayment).compareTo(bill.getTotalMoney().add(bill.getMoneyShip())) >= 0) {
            BillHistory history1 = new BillHistory();
            history1.setBill(bill);
            history1.setStatus(BillStatusConstant.XAC_NHAN_THONG_TIN_THANH_TOAN);
            history1.setNote("Đã thanh toán đủ tiền");
            billHistoryRepository.save(history1);
        }
        if(request.getType() == PaymentMethodConstant.TIEN_HOAN && bill.getStatus() == BillStatusConstant.TRA_HANG){
            bill.setMoneyReduce(BigDecimal.ZERO);
            billRepository.save(bill);
        }

        return new ResponseObject(paymentMethod);
    }
}
