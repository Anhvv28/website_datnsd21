package com.fpt.niceshoes.controller.admin;

import com.fpt.niceshoes.entity.Category;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.dto.request.properties.CategoryRequest;
import com.fpt.niceshoes.dto.response.CategoryResponse;
import com.fpt.niceshoes.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public PageableObject<CategoryResponse> getAll(CategoryRequest request) {
        return categoryService.getAll(request);
    }


    @GetMapping("/{id}")
    public Category getOne(@PathVariable Long id) {
        return categoryService.getOne(id);
    }

    @PostMapping
    public ResponseObject create(@RequestBody @Valid CategoryRequest request) {
        return new ResponseObject(categoryService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseObject update(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        return new ResponseObject(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseObject delete(@PathVariable Long id) {
        return new ResponseObject(categoryService.delete(id));
    }
}
