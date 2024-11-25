package com.fpt.niceshoes.infrastructure.common;

import com.fpt.niceshoes.infrastructure.constant.PaginationConstant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PageableRequest {

    private int page = PaginationConstant.DEFAULT_PAGE;
    private int sizePage = PaginationConstant.DEFAULT_SIZE;
}