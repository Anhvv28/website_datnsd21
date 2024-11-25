package com.fpt.niceshoes.service;

import com.fpt.niceshoes.entity.Category;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.dto.request.properties.CategoryRequest;
import com.fpt.niceshoes.dto.response.CategoryResponse;

public interface CategoryService {
    PageableObject<CategoryResponse> getAll(CategoryRequest request);

    Category getOne(Long id);

    Category create(CategoryRequest request);

    Category update(Long id, CategoryRequest request);

    Category delete(Long id);
}
