package com.fpt.niceshoes.service;

import com.fpt.niceshoes.entity.Voucher;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.request.VoucherRequest;
import com.fpt.niceshoes.dto.response.VoucherResponse;

import java.util.List;

public interface VoucherService {
    List<VoucherResponse> getAccountVoucher(Long id,VoucherRequest request);
    List<VoucherResponse> getPublicVoucher(VoucherRequest request);
    PageableObject<VoucherResponse> getAll(VoucherRequest request);
    VoucherResponse getOne(Long id);

    Voucher add(VoucherRequest voucher);

    Voucher update(Long id, VoucherRequest voucher);

    String delete(Long id);

    boolean isVoucherCodeExists(String code);

    void updateStatusVoucher();

    Voucher updateEndDate(Long id);
}
