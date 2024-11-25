package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.entity.Address;
import com.fpt.niceshoes.infrastructure.converter.AddressConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.dto.request.AddressRequest;
import com.fpt.niceshoes.dto.response.AddressResponse;
import com.fpt.niceshoes.repository.IAddressRepository;
import com.fpt.niceshoes.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private AddressConvert addressConvert;

    @Override
    public Page<AddressResponse> getByAccount(AddressRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return addressRepository.getAddress(request, pageable);
    }

    @Override
    public Address create(AddressRequest request) {
        return addressRepository.save(addressConvert.convertRequestToEntity(request));
    }

    @Override
    public Address update(Long idAddress, AddressRequest request) {
        Address addressUpdate = addressConvert.convertRequestToEntity(idAddress, request);
        if (request.getDefaultAddress()) {
            Address addressDefault = addressRepository.findByAccountIdAndDefaultAddress(addressUpdate.getAccount().getId(), true);
            if (addressDefault != null) {
                addressDefault.setDefaultAddress(false);
                addressRepository.save(addressDefault);
            }
         }
        return addressRepository.save(addressUpdate);
    }

    @Override
    public Address delete(Long idAddress) {
        Address address = addressRepository.findById(idAddress).get();

        if (addressRepository.findByAccountIdAndDeleted(address.getAccount().getId(), false).size() > 1) {
            address.setDeleted(true);
            return addressRepository.save(address);
        } else {
            throw new RestApiException("Không thể xóa địa chỉ này!");
        }
    }
}
