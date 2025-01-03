package com.fpt.niceshoes.controller.client;

import com.fpt.niceshoes.dto.request.properties.ColorRequest;
import com.fpt.niceshoes.dto.response.ColorResponse;
import com.fpt.niceshoes.entity.Color;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.service.ColorService;
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
@RequestMapping("/client/api/color")
public class ColorControllerClient {
    @Autowired
    private ColorService colorService;

    @GetMapping
    public PageableObject<ColorResponse> getAll(ColorRequest request) {
        return colorService.getAll(request);
    }

    @GetMapping("/{id}")
    public Color getOne(@PathVariable Long id) {
        return colorService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid ColorRequest request) {
        return new ResponseObject(colorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid ColorRequest request) {
        return new ResponseObject(colorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(colorService.delete(id));
    }
}
