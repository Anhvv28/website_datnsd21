package com.fpt.niceshoes.dto.request.bill;

import com.fpt.niceshoes.entity.BillDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillUpdateRequest {
    private BillDetail billDetail;
    private Integer quantity;
    private String address;
    private Boolean isDeleted;
}
