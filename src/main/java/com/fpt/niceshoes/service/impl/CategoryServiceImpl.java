package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.entity.Category;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.converter.CategoryConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.dto.request.properties.CategoryRequest;
import com.fpt.niceshoes.dto.response.CategoryResponse;
import com.fpt.niceshoes.repository.ICategoryRepository;
import com.fpt.niceshoes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ICategoryRepository repository;
    @Autowired
    private CategoryConvert categoryConvert;

    @Override
    public PageableObject<CategoryResponse> getAll(CategoryRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PageableObject<>(repository.getAllCategory(request, pageable));
    }

    @Override
    public Category getOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Category create(CategoryRequest request) {
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new RestApiException("Danh mục " + request.getName() + " đã tồn tại!");
        }
        Category category = categoryConvert.convertRequestToEntity(request);
        return repository.save(category);
    }

    @Override
    public Category update(Long id, CategoryRequest request) {
        Category oldCategory = repository.findById(id).get();
        if (repository.existsByNameIgnoreCase(request.getName())) {
            if (oldCategory.getName().equals(request.getName())) {
                return repository.save(categoryConvert.convertRequestToEntity(oldCategory, request));
            }
            throw new RestApiException("Danh mục  " + request.getName() + " đã tồn tại!");
        } else {
            return repository.save(categoryConvert.convertRequestToEntity(oldCategory, request));
        }
    }

    @Override
    public Category delete(Long id) {
        Category category = this.getOne(id);
        category.setDeleted(!category.getDeleted());
        return repository.save(category);
    }
}
