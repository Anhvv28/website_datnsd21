package com.fpt.niceshoes.dto.response;

import com.fpt.niceshoes.entity.Brand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(types = {Brand.class})
public interface BrandResponse {
    @Value("#{target.indexs}")
    Integer getIndex();

    Long getId();

    String getName();

    Boolean getStatus();

    LocalDateTime getCreateAt();
}
