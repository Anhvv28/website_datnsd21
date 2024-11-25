package com.fpt.niceshoes.service;



import com.fpt.niceshoes.entity.Promotion;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.PromotionRequest;
import com.fpt.niceshoes.dto.response.PromotionResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionService {
    PageableObject<PromotionResponse> getAll(PromotionRequest request);
    ResponseObject create(PromotionRequest request);
    ResponseObject update(Long id,PromotionRequest request);
    PromotionResponse getOne(Long id);
    List<Long> getListIdShoePromotion(Long idPromotion);
    List<Long> getListIdShoeDetailInPromotion(@Param("idPromotion") Long idPromotion);
    void deleteAll(Long idPromotion);
    void updateStatusPromotion();
    Promotion updateEndDate(Long id);
}
