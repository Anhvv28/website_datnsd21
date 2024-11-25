package com.fpt.niceshoes.controller.client;

import com.fpt.niceshoes.dto.request.shoe.FindShoeReqeust;
import com.fpt.niceshoes.dto.request.shoe.ShoeRequest;
import com.fpt.niceshoes.dto.response.ShoeResponse;
import com.fpt.niceshoes.dto.response.promotion.ShoePromotionResponse;
import com.fpt.niceshoes.entity.Shoe;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.service.ShoeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/api/shoe")
public class ShoeControllerClient {
    @Autowired
    private ShoeService shoeService;
    @GetMapping("/shoe-promotion")
    public List<ShoePromotionResponse> getTest(@RequestParam(required = false) Long promotion){
        return shoeService.getAllShoeInPromotion(promotion);
    }

    @GetMapping("/top-sell")
    public List<ShoeResponse> getTopSell(@RequestParam(required = false, defaultValue = "5") Integer top){
        return shoeService.getTopSell(top);
    }

    @GetMapping
    public PageableObject<ShoeResponse> getAll(FindShoeReqeust request) {
        return shoeService.getAll(request);
    }

    @GetMapping("/{id}")
    public Shoe getOne(@PathVariable Long id) {
        return shoeService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid ShoeRequest request) {
        return new ResponseObject(shoeService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid ShoeRequest request){
        return new ResponseObject(shoeService.update(id,request));
    }
}
