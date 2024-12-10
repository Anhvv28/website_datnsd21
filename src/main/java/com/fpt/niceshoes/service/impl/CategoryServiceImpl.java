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
        // Chuẩn hóa tên danh mục: loại bỏ khoảng trắng ở đầu và cuối
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên danh mục không được để trống!");
        }

        // Kiểm tra nếu danh mục đã tồn tại
        if (repository.existsByNameIgnoreCase(normalizedName)) {
            throw new RestApiException("Danh mục '" + normalizedName + "' đã tồn tại!");
        }

        // Chuyển đổi DTO thành thực thể Category và lưu
        Category category = categoryConvert.convertRequestToEntity(request);
        category.setName(normalizedName); // Đảm bảo lưu tên đã chuẩn hóa
        return repository.save(category);
    }

    @Override
    public Category update(Long id, CategoryRequest request) {
        Category oldCategory = repository.findById(id)
                .orElseThrow(() -> new RestApiException("Không tìm thấy danh mục với ID: " + id));

        // Chuẩn hóa tên danh mục
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên danh mục không được để trống!");
        }

        // Kiểm tra nếu danh mục đã tồn tại và không phải chính thực thể đang cập nhật
        if (repository.existsByNameIgnoreCase(normalizedName) &&
                !oldCategory.getName().equalsIgnoreCase(normalizedName)) {
            throw new RestApiException("Danh mục '" + normalizedName + "' đã tồn tại!");
        }

        // Cập nhật tên danh mục và lưu
        oldCategory.setName(normalizedName);
        return repository.save(oldCategory);
    }

    @Override
    public Category delete(Long id) {
        Category category = this.getOne(id);
        category.setDeleted(!category.getDeleted());
        return repository.save(category);
    }
}
