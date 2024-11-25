package com.fpt.niceshoes.controller.admin;

import com.fpt.niceshoes.entity.Sole;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.properties.SoleRequest;
import com.fpt.niceshoes.dto.response.SoleResponse;
import com.fpt.niceshoes.service.SoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sole")
public class SoleController {
    @Autowired
    private SoleService soleService;

    @GetMapping
    public PageableObject<SoleResponse> getAll(SoleRequest request) {
        return soleService.getAll(request);
    }

    @GetMapping("/{id}")
    public Sole getOne(@PathVariable Long id) {
        return soleService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid SoleRequest request) {
        return new ResponseObject(soleService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid SoleRequest request) {
        return new ResponseObject(soleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(soleService.delete(id));
    }
}
