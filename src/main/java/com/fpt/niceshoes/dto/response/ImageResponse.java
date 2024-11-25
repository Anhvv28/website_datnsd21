package com.fpt.niceshoes.dto.response;

import com.fpt.niceshoes.entity.Images;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Images.class})
public interface ImageResponse {
    Long getId();

    String getName();
}
