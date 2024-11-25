package com.fpt.niceshoes.controller.admin;

import com.fpt.niceshoes.entity.Size;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.properties.SizeRequest;
import com.fpt.niceshoes.dto.response.SizeResponse;
import com.fpt.niceshoes.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping
    public PageableObject<SizeResponse> getAll(SizeRequest request) {
        return sizeService.getAll(request);
    }

    @GetMapping("/{id}")
    public Size getOne(@PathVariable Long id) {
        return sizeService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid SizeRequest request) {
        return new ResponseObject(sizeService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid SizeRequest request) {
        return new ResponseObject(sizeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(sizeService.delete(id));
    }
}
