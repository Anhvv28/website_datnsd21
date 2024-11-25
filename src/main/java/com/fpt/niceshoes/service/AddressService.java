package com.fpt.niceshoes.service;

import com.fpt.niceshoes.entity.Address;
import com.fpt.niceshoes.dto.request.AddressRequest;
import com.fpt.niceshoes.dto.response.AddressResponse;
import org.springframework.data.domain.Page;

public interface AddressService {
    Page<AddressResponse> getByAccount(AddressRequest request);

    Address create(AddressRequest request);

    Address update(Long idAddress, AddressRequest request);

    Address delete(Long idAddress);
}
