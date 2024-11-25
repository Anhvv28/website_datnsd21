package com.fpt.niceshoes.dto.request.shoe;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShoeRequest {
    private Long id;
    @NotEmpty(message = "Tên không được để trống!")
    private String name;
    @NotNull(message = "Thương hiệu không được để trống!")
    private Long brand;
    @NotNull(message = "Danh mục không được để trống!")
    private Long category;
    private String description;
}
