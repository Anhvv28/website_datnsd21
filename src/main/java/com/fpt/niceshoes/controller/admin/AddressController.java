package com.fpt.niceshoes.controller.admin;

import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.AddressRequest;
import com.fpt.niceshoes.dto.response.AddressResponse;
import com.fpt.niceshoes.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @GetMapping("/{account}")
    public Page<AddressResponse> getByAccount(AddressRequest request){
        return addressService.getByAccount(request);
    }

    @PostMapping
    public ResponseObject create(@RequestBody AddressRequest request) {
        return new ResponseObject(addressService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id,
                                 @RequestBody AddressRequest request) {
        return new ResponseObject(addressService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(addressService.delete(id));
    }
}
