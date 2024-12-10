package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.entity.Size;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.converter.SizeConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.dto.request.properties.SizeRequest;
import com.fpt.niceshoes.dto.response.SizeResponse;
import com.fpt.niceshoes.repository.ISizeRepository;
import com.fpt.niceshoes.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    private ISizeRepository repository;
    @Autowired
    private SizeConvert sizeConvert;

    @Override
    public PageableObject<SizeResponse> getAll(SizeRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PageableObject<>(repository.getAllSize(request, pageable));
    }

    @Override
    public Size getOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Size create(SizeRequest request) {
        // Chuẩn hóa tên size bằng cách loại bỏ khoảng trắng ở đầu và cuối
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên size trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên size không được để trống!");
        }

        // Kiểm tra nếu tên size là số và không chứa ký tự đặc biệt
        if (!normalizedName.matches("\\d+")) {
            throw new RestApiException("Tên size phải là một số hợp lệ và không chứa ký tự đặc biệt!");
        }

        // Chuyển đổi tên size sang kiểu số nguyên
        int sizeValue = Integer.parseInt(normalizedName);

        // Kiểm tra nếu size không nằm trong khoảng 35-47
        if (sizeValue < 35 || sizeValue > 47) {
            throw new RestApiException("Size phải nằm trong khoảng từ 35 đến 47!");
        }

        // Kiểm tra nếu tên đã tồn tại
        if (repository.existsByNameIgnoreCase(normalizedName)) {
            throw new RestApiException("Size '" + normalizedName + "' đã tồn tại!");
        }

        // Chuyển đổi DTO thành thực thể và lưu
        Size size = sizeConvert.convertRequestToEntity(request);
        size.setName(normalizedName); // Đảm bảo lưu tên đã chuẩn hóa
        return repository.save(size);
    }

    @Override
    public Size update(Long id, SizeRequest request) {
        Size oldSize = repository.findById(id)
                .orElseThrow(() -> new RestApiException("Không tìm thấy size với ID: " + id));

        // Chuẩn hóa tên size
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên size trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên size không được để trống!");
        }

        // Kiểm tra nếu tên size là số và không chứa ký tự đặc biệt
        if (!normalizedName.matches("\\d+")) {
            throw new RestApiException("Tên size phải là một số hợp lệ và không chứa ký tự đặc biệt!");
        }

        // Chuyển đổi tên size sang kiểu số nguyên
        int sizeValue = Integer.parseInt(normalizedName);

        // Kiểm tra nếu size không nằm trong khoảng 35-47
        if (sizeValue < 35 || sizeValue > 47) {
            throw new RestApiException("Size phải nằm trong khoảng từ 35 đến 47!");
        }

        // Kiểm tra nếu tên đã tồn tại và không phải chính thực thể đang cập nhật
        if (repository.existsByNameIgnoreCase(normalizedName) &&
                !oldSize.getName().equalsIgnoreCase(normalizedName)) {
            throw new RestApiException("Size '" + normalizedName + "' đã tồn tại!");
        }

        // Cập nhật dữ liệu và lưu lại
        oldSize.setName(normalizedName);
        return repository.save(oldSize);
    }

    @Override
    public Size delete(Long id) {
        Size size = this.getOne(id);
        size.setDeleted(!size.getDeleted());
        return repository.save(size);
    }
}
