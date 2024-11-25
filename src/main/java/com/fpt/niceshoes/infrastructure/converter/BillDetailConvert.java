package com.fpt.niceshoes.infrastructure.converter;

import com.fpt.niceshoes.entity.Bill;
import com.fpt.niceshoes.entity.BillDetail;
import com.fpt.niceshoes.entity.PromotionDetail;
import com.fpt.niceshoes.entity.ShoeDetail;
import com.fpt.niceshoes.dto.request.billdetail.BillDetailRequest;
import com.fpt.niceshoes.repository.IBillRepository;
import com.fpt.niceshoes.repository.IPromotionDetailRepository;
import com.fpt.niceshoes.repository.IShoeDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillDetailConvert {
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private IShoeDetailRepository shoeDetailRepository;
    @Autowired
    private IPromotionDetailRepository promotionDetailRepository;

    public BillDetail convertRequestToEntity(BillDetailRequest request) {
        ShoeDetail shoeDetail = shoeDetailRepository.findByCode(request.getShoeDetail());
        Bill bill = billRepository.findById(request.getBill()).get();
        PromotionDetail promotionDetail = promotionDetailRepository.findByShoeDetailCode(request.getShoeDetail());
        return BillDetail.builder()
                .shoeDetail(shoeDetail)
                .bill(bill)
                .price(promotionDetail != null ? promotionDetail.getPromotionPrice() : shoeDetail.getPrice())
                .quantity(request.getQuantity())
                .status(false)
                .build();
    }

    public BillDetail convertRequestToEntity(BillDetail entity, BillDetailRequest request) {
        ShoeDetail shoeDetail = shoeDetailRepository.findByCode(request.getShoeDetail());
        Bill bill = billRepository.findById(request.getBill()).get();
        PromotionDetail promotionDetail = promotionDetailRepository.findByShoeDetailCode(request.getCode());
        entity.setShoeDetail(shoeDetail);
        entity.setBill(bill);
        entity.setPrice(promotionDetail != null ? promotionDetail.getPromotionPrice() : shoeDetail.getPrice());
        entity.setQuantity(entity.getQuantity() + request.getQuantity());
        return entity;
    }
}
