package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.dto.request.CartClientRequest;
import com.fpt.niceshoes.dto.request.bill.BillRequest;
import com.fpt.niceshoes.dto.request.bill.BillSearchRequest;
import com.fpt.niceshoes.dto.request.billdetail.BillClientRequest;
import com.fpt.niceshoes.dto.request.giveback.GivebackRequest;
import com.fpt.niceshoes.dto.response.BillResponse;
import com.fpt.niceshoes.dto.response.statistic.StatisticBillStatus;
import com.fpt.niceshoes.entity.*;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.infrastructure.constant.BillDetailStatusConstant;
import com.fpt.niceshoes.infrastructure.constant.BillStatusConstant;
import com.fpt.niceshoes.infrastructure.constant.NotificationType;
import com.fpt.niceshoes.infrastructure.constant.PaymentMethodConstant;
import com.fpt.niceshoes.infrastructure.constant.TyperOrderConstant;
import com.fpt.niceshoes.infrastructure.converter.BillConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.infrastructure.session.ShoseSession;
import com.fpt.niceshoes.repository.*;
import com.fpt.niceshoes.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private IBillHistoryRepository billHistoryRepository;
    @Autowired
    private BillConvert billConvert;
    @Autowired
    private INotificationRepository notificationRepository;
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IPaymentMethodRepository paymentMethodRepository;
    @Autowired
    private IVoucherRepository voucherRepository;
    @Autowired
    private IShoeDetailRepository shoeDetailRepository;
    @Autowired
    private IBillDetailRepository billDetailRepository;
    @Autowired
    private IPromotionDetailRepository promotionDetailRepository;
    @Autowired
    private ShoseSession session;

    @Override
    public PageableObject<BillResponse> getAll(BillSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PageableObject<>(billRepository.getAll(request, pageable));
    }

    @Override
    public List<Bill> getNewBill(BillSearchRequest request) {
        return billRepository.getNewBill(request);
    }

    @Override
    public Bill getOne(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public Bill findByCode(String code) {
        return billRepository.findByCode(code);
    }

    private String genBillCode() {
        String prefix = "HD150300";
        int x = 1;
        String code = prefix + x;
        while (billRepository.existsByCode(code)) {
            x++;
            code = prefix + x;
        }
        return code;
    }

    @Override
    public Bill create() {
        if (billRepository.findByAccountIdAndStatusAndDeletedFalse(session.getEmployee().getId(), BillStatusConstant.TAO_DON_HANG, PageRequest.of(0, 10)).getContent().size() >= 10) {
            throw new RestApiException("Chỉ được tạo tối đa 10 đơn hàng!");
        }
        Bill bill = new Bill();
        BillHistory billHistory = new BillHistory();
        bill.setAccount(accountRepository.findById(session.getEmployee().getId()).get());
        bill.setStatus(BillStatusConstant.TAO_DON_HANG);
        bill.setCode(this.genBillCode());
        Bill billSave = billRepository.save(bill);
        billHistory.setBill(billSave);
        billHistory.setStatus(billSave.getStatus());
        billHistory.setNote("Tạo đơn hàng");
        billHistoryRepository.save(billHistory);
        return billSave;
    }

    /// ban hang offline
    @Override
    public Bill orderBill(Long id, BillRequest request) {
        if (request.getVoucher() != null) {
            Voucher voucher = voucherRepository.findById(request.getVoucher()).get();
            voucher.setQuantity(voucher.getQuantity() - 1);
            voucherRepository.save(voucher);
        }
        BillHistory history = new BillHistory();
        PaymentMethod paymentMethod = new PaymentMethod();
        Bill bill = billConvert.convertRequestToEntity(billRepository.findById(id).get(), request);
        history.setBill(bill);
        paymentMethod.setBill(bill);
        paymentMethod.setType(PaymentMethodConstant.TIEN_KHACH_DUA);

        if (request.getWaitPay()) {
            bill.setStatus(BillStatusConstant.CHO_THANH_TOAN);
            history.setStatus(BillStatusConstant.CHO_THANH_TOAN);
            billHistoryRepository.save(history);
            billRepository.save(bill);
            return bill;
        }

        if (request.getType() == TyperOrderConstant.TAI_QUAY) {
            bill.setStatus(BillStatusConstant.HOAN_THANH);
            bill.setReceiveDate(System.currentTimeMillis());
            BillHistory history1 = new BillHistory();
            history1.setBill(bill);
            history1.setNote("Đã xác nhận thông tin thanh toán!");
            history1.setStatus(BillStatusConstant.XAC_NHAN_THONG_TIN_THANH_TOAN);
            billHistoryRepository.save(history1);
            if (request.getPaymentMethod() == PaymentMethodConstant.TIEN_MAT) {
                paymentMethod.setTotalMoney(bill.getTotalMoney());
                paymentMethod.setNote("Đã thanh toán tiền mặt!");
                paymentMethod.setMethod(PaymentMethodConstant.TIEN_MAT);
                paymentMethodRepository.save(paymentMethod);
            } else if (request.getPaymentMethod() == PaymentMethodConstant.CHUYEN_KHOAN) {
                paymentMethod.setTotalMoney(bill.getTotalMoney());
                paymentMethod.setNote("Đã chuyển khoản!");
                paymentMethod.setTradingCode(request.getTradingCode());
                paymentMethod.setMethod(PaymentMethodConstant.CHUYEN_KHOAN);
                paymentMethodRepository.save(paymentMethod);
            } else if (request.getPaymentMethod() == PaymentMethodConstant.TIEN_MAT_VA_CHUYEN_KHOAN) {
                PaymentMethod paymentMethod1 = new PaymentMethod();
                paymentMethod1.setBill(bill);
                paymentMethod1.setTotalMoney(request.getTienMat());
                paymentMethod1.setNote("Đã thanh toán!");
                paymentMethod1.setMethod(PaymentMethodConstant.TIEN_MAT);
                paymentMethod1.setType(PaymentMethodConstant.TIEN_KHACH_DUA);
                paymentMethodRepository.save(paymentMethod1);
                paymentMethod.setTotalMoney(request.getTienChuyenKhoan());
                paymentMethod.setTradingCode(request.getTradingCode());
                paymentMethod.setNote("Đã chuyển khoản!");
                paymentMethod.setMethod(PaymentMethodConstant.CHUYEN_KHOAN);
                paymentMethodRepository.save(paymentMethod);
            }
            history.setNote("Mua hàng thành công!");
            history.setStatus(BillStatusConstant.HOAN_THANH);
        } else if (request.getType() == TyperOrderConstant.GIAO_HANG) {
            bill.setStatus(BillStatusConstant.CHO_GIAO);
            history.setStatus(BillStatusConstant.CHO_GIAO);
            history.setNote("Chờ giao");
            if (request.getPaymentMethod() == PaymentMethodConstant.CHUYEN_KHOAN) {
                BillHistory history1 = new BillHistory();
                history1.setBill(bill);
                history1.setNote("Đã xác nhận thông tin thanh toán!");
                history1.setStatus(BillStatusConstant.XAC_NHAN_THONG_TIN_THANH_TOAN);
                billHistoryRepository.save(history1);
                paymentMethod.setTotalMoney(bill.getTotalMoney().add(bill.getMoneyShip()));
                paymentMethod.setNote("Đã chuyển khoản!");
                paymentMethod.setTradingCode(request.getTradingCode());
                paymentMethod.setMethod(PaymentMethodConstant.CHUYEN_KHOAN);
                paymentMethodRepository.save(paymentMethod);
            } else if (request.getPaymentMethod() == PaymentMethodConstant.TIEN_MAT_VA_CHUYEN_KHOAN) {
                paymentMethod.setTotalMoney(request.getTienChuyenKhoan());
                paymentMethod.setNote("Đã chuyển khoản!");
                paymentMethod.setTradingCode(request.getTradingCode());
                paymentMethod.setMethod(PaymentMethodConstant.CHUYEN_KHOAN);
                paymentMethodRepository.save(paymentMethod);
            }
        }
        billHistoryRepository.save(history);
        billRepository.save(bill);
        if (bill.getCustomer() != null) {
            Notification notification = new Notification();
            notification.setType(NotificationType.CHUA_DOC);
            notification.setAccount(bill.getCustomer());
            if (bill.getStatus() == BillStatusConstant.HOAN_THANH) {
                notification.setTitle("Đơn hàng #" + bill.getCode() + " đã được mua thành công");
                notification.setContent("Đơn hàng #" + bill.getCode() + " đã được mua thành công, hãy liên hệ với chúng tôi nếu sản phẩm có vấn đề");
                notificationRepository.save(notification);
            } else {
                notification.setTitle("Đơn hàng #" + bill.getCode() + " đã được xác nhận và đang chờ vận chuyển đi");
                notification.setContent("Đơn hàng #" + bill.getCode() + " đã được xác nhận và đang chờ vận chuyển đi." +
                        " Trong thời gian này, bạn vẫn có thể thay đổi số lượng sản phẩm hoặc địa chỉ nhận hàng nếu cần thiết.");
                notificationRepository.save(notification);
            }
        }
        return bill;
    }

    @Override
    public Bill updateBill() {
        return null;
    }

    /// ban hang online
    @Override
    @Transactional(rollbackFor = RestApiException.class)
    public ResponseObject createBillClient(BillClientRequest request) {
        Bill bill = new Bill();
        BillHistory billHistory = new BillHistory();
        if (request.getAccount() != null) {
            bill.setCustomer(accountRepository.findById(request.getAccount()).orElse(null));
        }
        bill.setStatus(BillStatusConstant.CHO_XAC_NHAN);
        bill.setCode(this.genBillCode());
        bill.setType(TyperOrderConstant.GIAO_HANG);
        bill.setNote(request.getNote());
        bill.setPhoneNumber(request.getPhoneNumber());
        bill.setCustomerName(request.getCustomerName());
        bill.setAddress(request.getSpecificAddress() + "##" + request.getWard() + "##" + request.getDistrict() + "##" + request.getProvince());
        bill.setMoneyShip(request.getMoneyShip());
        bill.setMoneyReduce(request.getMoneyReduce());
        bill.setTotalMoney(request.getTotalMoney());
        if(request.getVoucher() != null){
            Voucher voucher = voucherRepository.findById(request.getVoucher()).get();
            voucher.setQuantity(voucher.getQuantity()-1);
            voucherRepository.save(voucher);
            bill.setVoucher(voucher);
        }

        Bill billSave = billRepository.save(bill);
        billHistory.setBill(billSave);
        billHistory.setStatus(billSave.getStatus());
        billHistory.setNote("Chờ xác nhận");
        billHistoryRepository.save(billHistory);

        for (CartClientRequest x : request.getCarts()) {
            ShoeDetail shoeDetail = shoeDetailRepository.findById(x.getId())
                    .orElseThrow(() -> new RestApiException("Không tìm thấy sản phẩm"));

            // Kiểm tra xem còn đủ hàng để đặt không
            if(shoeDetail.getQuantity() <= 0){
                throw new RestApiException("Sản phẩm " + shoeDetail.getShoe().getName()
                        + " [" + shoeDetail.getColor().getName()+"-" + shoeDetail.getSize().getName()+"] đã hết hàng!");
            }
            if(shoeDetail.getQuantity() < x.getQuantity()){
                throw new RestApiException(shoeDetail.getShoe().getName()
                        + " [" + shoeDetail.getColor().getName()+"-" + shoeDetail.getSize().getName()+"] chỉ được mua tối đa " + shoeDetail.getQuantity() + " sản phẩm!");
            }

            // Tạo BillDetail
            BillDetail billDetail = new BillDetail();
            billDetail.setBill(bill);
            billDetail.setQuantity(x.getQuantity());
            billDetail.setShoeDetail(shoeDetail);
            billDetail.setPrice(shoeDetail.getPrice());
            billDetail.setStatus(false);
            billDetailRepository.save(billDetail);

            // (1) Không trừ shoeDetail tại đây
            // shoeDetail.setQuantity(shoeDetail.getQuantity() - x.getQuantity());
            // shoeDetailRepository.save(shoeDetail);
        }
        if (bill.getCustomer() != null) {
            Account account = bill.getCustomer();
            Notification notification = new Notification();
            notification.setTitle("Đơn hàng của bạn đã được đặt");
            notification.setContent("Xin chào " + account.getName() + ", đơn hàng với mã vận đơn " +
                    bill.getCode() + " đã được hệ thống ghi nhận và đang chờ nhân viên xác nhận. " +
                    "Cảm ơn bạn đã dành thời gian cho InnoString Stride!");
            notification.setAccount(account);
            notification.setType(NotificationType.CHUA_DOC);
            notificationRepository.save(notification);
        }
        return new ResponseObject(bill);
    }

    @Override
    @Transactional(rollbackFor = RestApiException.class)
    public ResponseObject createBillClientVnpay(BillClientRequest request, String code) {
        Bill bill = new Bill();
        BillHistory billHistory = new BillHistory();
        if (request.getAccount() != null) {
            bill.setCustomer(accountRepository.findById(request.getAccount()).orElse(null));
        }
        bill.setStatus(BillStatusConstant.CHO_XAC_NHAN);
        if(request.getVoucher() != null){
            Voucher voucher = voucherRepository.findById(request.getVoucher()).get();
            voucher.setQuantity(voucher.getQuantity()-1);
            voucherRepository.save(voucher);
            bill.setVoucher(voucher);
        }
        bill.setCode(this.genBillCode());
        bill.setType(TyperOrderConstant.GIAO_HANG);
        bill.setNote(request.getNote());
        bill.setPhoneNumber(request.getPhoneNumber());
        bill.setCustomerName(request.getCustomerName());
        bill.setAddress(request.getSpecificAddress() + "##" + request.getWard() + "##" + request.getDistrict() + "##" + request.getProvince());
        bill.setMoneyShip(request.getMoneyShip());
        bill.setMoneyReduce(request.getMoneyReduce());
        bill.setTotalMoney(request.getTotalMoney());
        if (request.getVoucher() != null) {
            bill.setVoucher(voucherRepository.findById(request.getVoucher()).get());
        }

        Bill billSave = billRepository.save(bill);
        billHistory.setBill(billSave);
        billHistory.setStatus(billSave.getStatus());
        billHistory.setNote("Chờ xác nhận");
        billHistoryRepository.save(billHistory);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setBill(billSave);
        paymentMethod.setType(PaymentMethodConstant.TIEN_KHACH_DUA);
        paymentMethod.setMethod(PaymentMethodConstant.CHUYEN_KHOAN);
        paymentMethod.setTradingCode(code);
        paymentMethod.setTotalMoney(billSave.getTotalMoney().add(billSave.getMoneyShip()));
        paymentMethod.setNote("Đã thanh toán");
        paymentMethodRepository.save(paymentMethod);

        for (CartClientRequest x : request.getCarts()) {
            ShoeDetail shoeDetail = shoeDetailRepository.findById(x.getId()).get();
            BillDetail billDetail = new BillDetail();
            billDetail.setBill(bill);
            billDetail.setQuantity(x.getQuantity());
            billDetail.setShoeDetail(shoeDetail);
            billDetail.setPrice(shoeDetail.getPrice());
            billDetail.setStatus(false);
            billDetailRepository.save(billDetail);
            shoeDetail.setQuantity(shoeDetail.getQuantity() - x.getQuantity());
            if(shoeDetail.getQuantity() < 0){
                throw new RestApiException("Sản phẩm này đã hết hàng!");
            }
            shoeDetailRepository.save(shoeDetail);
        }
        if (bill.getCustomer() != null) {
            Account account = bill.getCustomer();
            Notification notification = new Notification();
            notification.setTitle("Đơn hàng của bạn đã được đặt");
            notification.setContent("Xin chào " + account.getName() + ", đơn hàng với mã vận đơn " +
                    bill.getCode() + " đã được hệ thống ghi nhận và đang chờ nhân viên xác nhận. " +
                    "Cảm ơn bạn đã dành thời gian cho NiceShoes!");
            notification.setAccount(account);
            notification.setType(NotificationType.CHUA_DOC);
            notificationRepository.save(notification);
        }
        return new ResponseObject(bill);
    }

    @Override
    public Bill delete(Long id) {
        return null;
    }


    @Override
    @Transactional(rollbackFor = RestApiException.class)
    public Bill changeStatus(Long id, String note, Boolean isCancel) {
        // 1. Lấy Bill & khởi tạo BillHistory
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RestApiException("Không tìm thấy hoá đơn!"));
        BillHistory history = new BillHistory();
        history.setBill(bill);
        history.setNote(note);

        // 2. Nếu huỷ đơn => KHÔNG phục hồi số lượng
        if (isCancel) {
            // Không quan tâm bill đang ở trạng thái gì, không cộng lại quantity
            bill.setStatus(BillStatusConstant.DA_HUY);
            history.setStatus(BillStatusConstant.DA_HUY);

        } else {
            // 3. Chuyển trạng thái nếu không huỷ
            int currentStatus = bill.getStatus();

            switch (currentStatus) {
                // Chưa xác nhận => chuyển sang CHO_GIAO
                case BillStatusConstant.CHO_XAC_NHAN:
                    bill.setStatus(BillStatusConstant.CHO_GIAO);
                    history.setStatus(BillStatusConstant.CHO_GIAO);
                    break;

                // CHO_GIAO => chuyển sang DANG_GIAO
                case BillStatusConstant.CHO_GIAO:
                    bill.setStatus(BillStatusConstant.DANG_GIAO);
                    history.setStatus(BillStatusConstant.DANG_GIAO);
                    // Ví dụ: set ngày xuất kho
                    bill.setShipDate(new Date());
                    break;

                // DANG_GIAO => kiểm tra thanh toán đủ => chuyển HOÀN_THÀNH + trừ kho
                case BillStatusConstant.DANG_GIAO:
                    // Ví dụ kiểm tra thanh toán
                    if (!checkPaymentIsEnough(bill)) {
                        throw new RestApiException("Vui lòng hoàn tất thanh toán trước khi hoàn thành!");
                    }
                    bill.setStatus(BillStatusConstant.HOAN_THANH);
                    history.setStatus(BillStatusConstant.HOAN_THANH);

                    // CHỈ KHI HOÀN THÀNH MỚI TRỪ KHO (nếu bạn chưa trừ khi tạo đơn)
                    List<BillDetail> details = billDetailRepository.findByBillId(bill.getId());
                    for (BillDetail bd : details) {
                        ShoeDetail shoeDetail = bd.getShoeDetail();

                        // Kiểm tra tồn kho lần cuối
                        if (shoeDetail.getQuantity() < bd.getQuantity()) {
                            throw new RestApiException(
                                    "Không đủ số lượng cho " + shoeDetail.getShoe().getName()
                                            + " [" + shoeDetail.getColor().getName() + "-"
                                            + shoeDetail.getSize().getName() + "]"
                            );
                        }
                        // Trừ kho
                        shoeDetail.setQuantity(shoeDetail.getQuantity() - bd.getQuantity());
                        shoeDetailRepository.save(shoeDetail);
                    }
                    // Ghi lại thời gian nhận
                    bill.setReceiveDate(System.currentTimeMillis());
                    break;

                // Nếu đã HOÀN_THÀNH => Không được chuyển nữa (tránh trừ thêm)
                case BillStatusConstant.HOAN_THANH:
                    throw new RestApiException("Đơn hàng đã hoàn thành, không thể cập nhật trạng thái nữa!");

                    // Nếu đã HUỶ => Không được chuyển nữa
                case BillStatusConstant.DA_HUY:
                    throw new RestApiException("Đơn hàng đã huỷ, không thể cập nhật trạng thái!");

                    // Hoặc tùy logic bạn, có thể +1 status
                default:
                    // Nếu bạn có nhiều trạng thái khác, xử lý tại đây
                    bill.setStatus(currentStatus + 1);
                    history.setStatus(bill.getStatus());
                    break;
            }
        }

        // 4. Lưu hoá đơn & lưu lịch sử
        Bill billSaved = billRepository.save(bill);
        history.setBill(billSaved);
        billHistoryRepository.save(history);

        // 5. Gửi thông báo nếu cần
        if (billSaved.getCustomer() != null) {
            Account account = billSaved.getCustomer();
            Notification notification = new Notification();
            notification.setAccount(account);
            notification.setType(NotificationType.CHUA_DOC);

            switch (billSaved.getStatus()) {
                case BillStatusConstant.CHO_GIAO:
                    notification.setTitle("Đơn hàng #" + billSaved.getCode() + " đã xác nhận");
                    notification.setContent("Chào " + account.getName()
                            + ", đơn hàng #" + billSaved.getCode()
                            + " đang chờ vận chuyển!");
                    break;
                case BillStatusConstant.DANG_GIAO:
                    notification.setTitle("Đơn hàng #" + billSaved.getCode() + " đang trên đường giao");
                    notification.setContent("Chào " + account.getName()
                            + ", đơn hàng #" + billSaved.getCode()
                            + " đang được vận chuyển đến bạn. Vui lòng chú ý điện thoại!");
                    break;
                case BillStatusConstant.HOAN_THANH:
                    notification.setTitle("Đơn hàng #" + billSaved.getCode() + " đã hoàn thành");
                    notification.setContent("Chào " + account.getName()
                            + ", đơn hàng #" + billSaved.getCode()
                            + " đã được giao thành công. Cảm ơn bạn!");
                    break;
                case BillStatusConstant.DA_HUY:
                    notification.setTitle("Đơn hàng #" + billSaved.getCode() + " đã bị hủy");
                    notification.setContent("Chào " + account.getName()
                            + ", đơn hàng #" + billSaved.getCode()
                            + " đã bị hủy theo yêu cầu!");
                    break;
                default:
                    break;
            }
            notificationRepository.save(notification);
        }

        return billSaved;
    }

    private boolean checkPaymentIsEnough(Bill bill) {
        List<PaymentMethod> methods = paymentMethodRepository
                .findByBillIdAndType(bill.getId(), PaymentMethodConstant.TIEN_KHACH_DUA);

        BigDecimal totalPayment = BigDecimal.ZERO;
        for (PaymentMethod pm : methods) {
            totalPayment = totalPayment.add(pm.getTotalMoney());
        }

        BigDecimal needed = bill.getTotalMoney().add(bill.getMoneyShip());
        return totalPayment.compareTo(needed) >= 0;
    }



    @Override
    public Bill changeInfoCustomer(Long id, BillRequest request) {
        Bill bill = billRepository.findById(id).orElse(null);
        bill.setCustomerName(request.getCustomerName());
        bill.setPhoneNumber(request.getPhoneNumber());
        bill.setEmail(request.getEmail());
        bill.setAddress(request.getAddress());
        bill.setMoneyShip(request.getMoneyShip());

        BillHistory history = new BillHistory();
        history.setBill(bill);
        history.setStatus(BillStatusConstant.CHINH_SUA_DON_HANG);
        history.setNote("Cập nhật thông tin khách hàng");
        billHistoryRepository.save(history);
        return billRepository.save(bill);
    }

    @Override
    public List<StatisticBillStatus> statisticBillStatus() {
        return billRepository.statisticBillStatus();
    }

    @Override
    public ResponseObject givebackAll(Long idBill, String note) {
        Bill bill = billRepository.findById(idBill).orElseThrow(() -> new RestApiException("Bill not found!"));

        // Mark the bill as canceled
        bill.setStatus(BillStatusConstant.DA_HUY);
        bill.setTotalMoney(BigDecimal.ZERO); // Reset the total amount
        bill.setMoneyReduce(BigDecimal.ZERO); // Reset any discount
        billRepository.save(bill);

        // Update all bill details to returned
        billDetailRepository.findByBillId(idBill).forEach(billDetail -> {
            billDetail.setStatus(BillDetailStatusConstant.TRA_HANG);
            billDetailRepository.save(billDetail);
        });

        // Record the return event in history
        BillHistory returnHistory = new BillHistory();
        returnHistory.setBill(bill);
        returnHistory.setNote(note);
        returnHistory.setStatus(BillStatusConstant.TRA_HANG);
        billHistoryRepository.save(returnHistory);

        // Record the cancellation in history
        BillHistory cancelHistory = new BillHistory();
        cancelHistory.setBill(bill);
        cancelHistory.setNote("Đơn hàng đã bị hủy");
        cancelHistory.setStatus(BillStatusConstant.DA_HUY);
        billHistoryRepository.save(cancelHistory);

        // Notify the customer if associated
        if (bill.getCustomer() != null) {
            Notification notification = new Notification();
            notification.setType(NotificationType.CHUA_DOC);
            notification.setAccount(bill.getCustomer());
            notification.setTitle("Đơn hàng #" + bill.getCode() + " đã bị hủy");
            notification.setContent("Xin chào " + bill.getCustomer().getName() +
                    ". Đơn hàng #" + bill.getCode() + " đã bị hủy do trả toàn bộ sản phẩm trong hóa đơn.");
            // Save notification (add this line if your NotificationRepository exists)
            // notificationRepository.save(notification);
        }

        return new ResponseObject(bill);
    }


    @Override
    @Transactional(rollbackFor = RestApiException.class)
    public ResponseObject giveback(GivebackRequest request) {
        // Fetch the BillDetail and associated Bill
        BillDetail billDetail = billDetailRepository.findById(request.getBillDetail())
                .orElseThrow(() -> new RestApiException("Không tìm thấy chi tiết hóa đơn!"));
        ShoeDetail shoeDetail = billDetail.getShoeDetail();
        Bill bill = billDetail.getBill();

        // **Đặt trạng thái TRA_HANG ngay từ đầu** (Điểm mấu chốt của đoạn mã thứ 2)
        bill.setStatus(BillStatusConstant.TRA_HANG);
        billRepository.save(bill); // Lưu lại trạng thái của Bill ngay lập tức

        // Validate refund quantity
        if (request.getQuantity() <= 0 || request.getQuantity() > billDetail.getQuantity()) {
            throw new RestApiException("Số lượng không hợp lệ!");
        }

        // Determine the price of the product
        PromotionDetail promotionDetail = promotionDetailRepository.findByShoeDetailId(shoeDetail.getId());
        BigDecimal priceToUse = (promotionDetail != null && promotionDetail.getPromotionPrice() != null)
                ? promotionDetail.getPromotionPrice()
                : billDetail.getPrice();

        // Calculate the total refund price
        BigDecimal totalRefundPrice = priceToUse.multiply(BigDecimal.valueOf(request.getQuantity()));

        // Update BillDetail
        if (request.getQuantity().equals(billDetail.getQuantity())) {
            billDetail.setStatus(BillDetailStatusConstant.TRA_HANG);
        } else {
            billDetail.setQuantity(billDetail.getQuantity() - request.getQuantity());
        }
        billDetailRepository.save(billDetail);

        // Recalculate total value of the bill before discounts
        BigDecimal newTotalBeforeDiscount = billDetailRepository.findByBillId(bill.getId())
                .stream()
                .filter(detail -> detail.getStatus() != BillDetailStatusConstant.TRA_HANG)
                .map(detail -> {
                    PromotionDetail detailPromotion = promotionDetailRepository.findByShoeDetailId(detail.getShoeDetail().getId());
                    return (detailPromotion != null && detailPromotion.getPromotionPrice() != null)
                            ? detailPromotion.getPromotionPrice().multiply(BigDecimal.valueOf(detail.getQuantity()))
                            : detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Check if voucher is still applicable
        BigDecimal refundAmount;
        BigDecimal initialBillTotal = bill.getTotalMoney(); // Total paid by the customer
        Voucher voucher = bill.getVoucher();

        if (voucher != null && newTotalBeforeDiscount.compareTo(voucher.getMinBillValue()) >= 0) {
            BigDecimal newMoneyReduce = newTotalBeforeDiscount.multiply(BigDecimal.valueOf(voucher.getPercentReduce()))
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            bill.setMoneyReduce(newMoneyReduce);
            bill.setTotalMoney(newTotalBeforeDiscount.subtract(newMoneyReduce));
        } else {
            bill.setMoneyReduce(BigDecimal.ZERO);
            bill.setVoucher(null);
            bill.setTotalMoney(newTotalBeforeDiscount);
        }

        // Calculate the refund amount
        refundAmount = initialBillTotal.subtract(bill.getTotalMoney());

        // Save updated Bill
        billRepository.save(bill);

        // Record the refund in BillHistory
        BillHistory history = new BillHistory();
        history.setBill(bill);
        history.setStatus(BillStatusConstant.TRA_HANG);
        history.setNote("Trả sản phẩm \"" + shoeDetail.getShoe().getName() + " [" + shoeDetail.getColor().getName() +
                "-" + shoeDetail.getSize().getName() + "]\" - Số lượng: \"" + request.getQuantity() + "\". Lý do: " + request.getNote());
        billHistoryRepository.save(history);

        // Check if all BillDetails have been refunded
        boolean allRefunded = billDetailRepository.findByBillAndStatus(bill, false).isEmpty();
        if (allRefunded) {
            bill.setStatus(BillStatusConstant.DA_HUY);
            billRepository.save(bill);

            // Record bill cancellation in BillHistory
            BillHistory cancelHistory = new BillHistory();
            cancelHistory.setBill(bill);
            cancelHistory.setNote("Đơn hàng đã bị hủy");
            cancelHistory.setStatus(BillStatusConstant.DA_HUY);
            billHistoryRepository.save(cancelHistory);
        }

        // Return refund amount to the frontend
        return new ResponseObject(refundAmount);
    }




    public BigDecimal getTotalRefundAmount() {
        return billRepository.calculateTotalRefundAmount(Arrays.asList(7, 8));
    }

}
