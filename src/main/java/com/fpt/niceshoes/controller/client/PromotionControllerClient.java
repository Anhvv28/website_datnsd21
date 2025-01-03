package com.fpt.niceshoes.controller.client;

import com.fpt.niceshoes.dto.request.PromotionRequest;
import com.fpt.niceshoes.dto.response.PromotionResponse;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/api/promotion")
public class PromotionControllerClient {
    @Autowired
    private PromotionService service;

    @GetMapping
    public PageableObject getAll(PromotionRequest request) {
        return service.getAll(request);
    }
    @GetMapping("/{id}")
    public PromotionResponse getOne(@PathVariable Long id){
        return service.getOne(id);
    }
    @GetMapping("/list-shoe-id")
    public List<Long> getListIdShoePromotion(@RequestParam(required = false) Long idPromotion){
        return service.getListIdShoePromotion(idPromotion);
    }

    @GetMapping("/list-shoe-detail-id")
    public List<Long> getListIdShoeDetailPromotion(@RequestParam(required = false) Long idPromotion){
        return service.getListIdShoeDetailInPromotion(idPromotion);
    }

    @PostMapping
    public ResponseObject create(@RequestBody PromotionRequest request){
        return service.create(request);
    }
}
