package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.entity.Bill;
import com.fpt.niceshoes.entity.BillDetail;
import com.fpt.niceshoes.entity.BillHistory;
import com.fpt.niceshoes.entity.PromotionDetail;
import com.fpt.niceshoes.entity.ShoeDetail;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.constant.BillStatusConstant;
import com.fpt.niceshoes.infrastructure.converter.BillDetailConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.dto.request.billdetail.BillDetailRequest;
import com.fpt.niceshoes.dto.response.BillDetailResponse;
import com.fpt.niceshoes.repository.IBillDetailRepository;
import com.fpt.niceshoes.repository.IBillHistoryRepository;
import com.fpt.niceshoes.repository.IBillRepository;
import com.fpt.niceshoes.repository.IPromotionDetailRepository;
import com.fpt.niceshoes.repository.IShoeDetailRepository;
import com.fpt.niceshoes.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BillDetailServiceImpl implements BillDetailService {

    @Autowired
    private IBillDetailRepository billDetailRepository;
    @Autowired
    private BillDetailConvert billDetailConvert;
    @Autowired
    private IShoeDetailRepository shoeDetailRepository;
    @Autowired
    private IPromotionDetailRepository promotionDetailRepository;
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private IBillHistoryRepository billHistoryRepository;

    @Override
    public PageableObject<BillDetailResponse> getAll(BillDetailRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PageableObject<>(billDetailRepository.getAllBillDetail(request, pageable));
    }

    @Override
    public BillDetail getOne(Long id) {
        return billDetailRepository.findById(id).orElse(null);
    }

    @Override
    public BillDetail create(BillDetailRequest request) {
        PromotionDetail promotionDetail = promotionDetailRepository.findByShoeDetailCode(request.getShoeDetail());
        BillDetail billDetail = billDetailConvert.convertRequestToEntity(request);
        ShoeDetail shoeDetail = shoeDetailRepository.findByCode(request.getShoeDetail());
        if (request.getQuantity() < 1) {
            throw new RestApiException("Số lượng phải lớn hơn 1!");
        } else if (request.getQuantity() > shoeDetail.getQuantity()) {
            throw new RestApiException("Quá số lượng cho phép!");
        }
        shoeDetail.setQuantity(shoeDetail.getQuantity() - request.getQuantity());
        shoeDetailRepository.save(shoeDetail);
        BillDetail existBillDetail = billDetailRepository.findByShoeDetailCodeAndBillIdAndStatus(request.getShoeDetail(), request.getBill(), false);
        if (existBillDetail != null) {
            existBillDetail.setPrice(promotionDetail != null ? promotionDetail.getPromotionPrice() : shoeDetail.getPrice());
            existBillDetail.setQuantity(existBillDetail.getQuantity() + request.getQuantity());
            if(existBillDetail.getPrice().compareTo(request.getPrice()) < 0){
                existBillDetail.setPrice(request.getPrice());
            }
            return billDetailRepository.save(existBillDetail);
        }
        BillDetail billDetail1 = billDetailRepository.save(billDetail);

        Bill bill = billDetail1.getBill();
        if (bill.getStatus() != BillStatusConstant.TAO_DON_HANG) {
            Double caculateTotalMoney = 0.0;
            for (BillDetail x : billDetailRepository.findByBillId(billDetail.getBill().getId())) {
                caculateTotalMoney += x.getQuantity() * x.getPrice().doubleValue();
            }
            bill.setTotalMoney(BigDecimal.valueOf(caculateTotalMoney).subtract(bill.getMoneyReduce()));

            if (bill.getStatus() == BillStatusConstant.CHO_GIAO || bill.getStatus() == BillStatusConstant.CHO_XAC_NHAN || bill.getStatus() == BillStatusConstant.CHO_THANH_TOAN) {
                BillHistory billHistory = new BillHistory();
                billHistory.setBill(bill);
                billHistory.setNote("Đã thêm " + request.getQuantity() + " giày \"" + shoeDetail.getShoe().getName() + " [" + shoeDetail.getColor().getName() + "-" + shoeDetail.getSize().getName() + "]\"");
                billHistory.setStatus(BillStatusConstant.CHINH_SUA_DON_HANG);
                billHistoryRepository.save(billHistory);
            }
            billRepository.save(bill);
        }
        return billDetail1;
    }

    @Override
    public BillDetail update(Long id, BillDetailRequest request) {
        BillDetail old = billDetailRepository.findById(id).get();
        return billDetailRepository.save(billDetailConvert.convertRequestToEntity(old, request));
    }

    @Override
    public BillDetail delete(Long id) {
        BillDetail billDetail = billDetailRepository.findById(id).get();
        ShoeDetail shoeDetail = billDetail.getShoeDetail();
        shoeDetail.setQuantity(shoeDetail.getQuantity() + billDetail.getQuantity());
        billDetailRepository.delete(billDetail);

        Bill bill = billDetail.getBill();
        if (bill.getStatus() != BillStatusConstant.TAO_DON_HANG) {
            Double caculateTotalMoney = 0.0;
            for (BillDetail x : billDetailRepository.findByBillId(billDetail.getBill().getId())) {
                caculateTotalMoney += x.getQuantity() * x.getPrice().doubleValue();
            }
            bill.setTotalMoney(BigDecimal.valueOf(caculateTotalMoney).subtract(bill.getMoneyReduce()));

            if (bill.getStatus() == BillStatusConstant.CHO_GIAO || bill.getStatus() == BillStatusConstant.CHO_XAC_NHAN || bill.getStatus() == BillStatusConstant.CHO_THANH_TOAN) {
                BillHistory billHistory = new BillHistory();
                billHistory.setBill(bill);
                billHistory.setNote("Đã xóa " + " giày \"" + shoeDetail.getShoe().getName() + " [" + shoeDetail.getColor().getName() + "-" + shoeDetail.getSize().getName() + "]\"");
                billHistory.setStatus(BillStatusConstant.CHINH_SUA_DON_HANG);
                billHistoryRepository.save(billHistory);
            }
            billRepository.save(bill);
        }
        return billDetail;
    }

    @Override
    @Transactional(rollbackFor = RestApiException.class)
    public BillDetail updateQuantity(Long id, Integer newQuantity, BigDecimal price) {
        // 1. Tìm BillDetail theo ID một cách an toàn
        BillDetail billDetail = billDetailRepository.findById(id)
                .orElseThrow(() -> new RestApiException("Không tìm thấy chi tiết hóa đơn!"));

        // 2. Lấy ShoeDetail từ BillDetail
        ShoeDetail shoeDetail = billDetail.getShoeDetail();

        // 3. Kiểm tra số lượng mới hợp lệ
        if (newQuantity > (shoeDetail.getQuantity() + billDetail.getQuantity())) {
            throw new RestApiException("Quá số lượng cho phép!");
        }
        if (newQuantity <= 0) {
            throw new RestApiException("Vui lòng nhập số lượng hợp lệ!");
        }

        // 4. Cập nhật số lượng và tồn kho sản phẩm
        shoeDetail.setQuantity(shoeDetail.getQuantity() + billDetail.getQuantity() - newQuantity);
        billDetail.setQuantity(newQuantity);

        // 5. Tìm PromotionPrice nếu có
        BigDecimal promotionPrice = promotionDetailRepository.findPromotionPriceByShoeDetailId(shoeDetail.getId());

        // 6. Xác định giá cần sử dụng
        BigDecimal priceToUse = price; // Giá từ frontend

        if (promotionPrice != null && promotionPrice.compareTo(price) < 0) {
            priceToUse = promotionPrice;
        }

//        logger.info("PromotionPrice: {}", promotionPrice);
//        logger.info("Price from frontend: {}", price);
//        logger.info("Price to use: {}", priceToUse);

        // 7. Cập nhật giá nếu cần
        if (billDetail.getPrice().compareTo(priceToUse) != 0) {
            billDetail.setPrice(priceToUse);
        }

        // 8. Lưu các thay đổi
        billDetailRepository.save(billDetail);
        shoeDetailRepository.save(shoeDetail);

        // 9. Cập nhật tổng tiền của Bill
        Bill bill = billDetail.getBill();
        if (bill.getStatus() != BillStatusConstant.TAO_DON_HANG) {
            Double calculateTotalMoney = 0.0;
            for (BillDetail x : billDetailRepository.findByBillId(bill.getId())) {
                calculateTotalMoney += x.getQuantity() * x.getPrice().doubleValue();
            }
            bill.setTotalMoney(BigDecimal.valueOf(calculateTotalMoney).subtract(bill.getMoneyReduce()));

            // Thêm lịch sử nếu trạng thái phù hợp
            if (bill.getStatus() == BillStatusConstant.CHO_GIAO ||
                    bill.getStatus() == BillStatusConstant.CHO_XAC_NHAN ||
                    bill.getStatus() == BillStatusConstant.CHO_THANH_TOAN) {

                BillHistory billHistory = new BillHistory();
                billHistory.setBill(bill);
                billHistory.setNote("Đã sửa số lượng giày \"" +
                        shoeDetail.getShoe().getName() + " [" +
                        shoeDetail.getColor().getName() + "-" +
                        shoeDetail.getSize().getName() + "]\" lên " + newQuantity);
                billHistory.setStatus(BillStatusConstant.CHINH_SUA_DON_HANG);
                billHistoryRepository.save(billHistory);
            }

            // Lưu Bill
            billRepository.save(bill);
        }

        // 10. Trả về BillDetail đã cập nhật
        return billDetail;
    }

    @Override
    public Map<String, Object> getMostCanceledProduct() {
        Optional<Object[]> result = billDetailRepository.findMostCanceledProduct();
        if (result.isEmpty()) {
            return Map.of("productName", "Không có", "count", 0);
        }
        Object[] data = result.get();
        Map<String, Object> map = new HashMap<>();
        map.put("productName", data[0]);
        map.put("count", data[1]);
        return map;
    }

    @Override
    public Map<String, Object> getMostReturnedProduct() {
        Optional<Object[]> result = billDetailRepository.findMostReturnedProduct();
        if (result.isEmpty()) {
            return Map.of("productName", "Không có", "count", 0);
        }
        Object[] data = result.get();
        Map<String, Object> map = new HashMap<>();
        map.put("productName", data[0]);
        map.put("count", data[1]);
        return map;
    }


}
