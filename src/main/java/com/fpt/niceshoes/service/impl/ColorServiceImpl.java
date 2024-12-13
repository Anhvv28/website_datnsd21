package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.entity.Color;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.converter.ColorConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.dto.request.properties.ColorRequest;
import com.fpt.niceshoes.dto.response.ColorResponse;
import com.fpt.niceshoes.repository.IColorRepository;
import com.fpt.niceshoes.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private IColorRepository repository;
    @Autowired
    private ColorConvert colorConvert;

    @Override
    public PageableObject<ColorResponse> getAll(ColorRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PageableObject<>(repository.getAllColor(request, pageable));
    }

    @Override
    public Color getOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Color create(ColorRequest request) {
        // Chuẩn hóa tên: loại bỏ khoảng trắng ở đầu và cuối, kiểm tra trống
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên màu không được để trống!");
        }
        // Kiểm tra sự tồn tại của tên đã chuẩn hóa
        if (repository.existsByNameIgnoreCase(normalizedName)) {
            throw new RestApiException("Màu '" + normalizedName + "' đã tồn tại!");
        }

        // Chuyển DTO thành thực thể và lưu
        Color color = colorConvert.convertRequestToEntity(request);
        color.setName(normalizedName); // Đảm bảo tên được lưu không chứa khoảng trắng thừa
        return repository.save(color);
    }

    @Override
    public Color update(Long id, ColorRequest request) {
        // Tìm đối tượng Color theo id
        Color oldColor = repository.findById(id).orElseThrow(() ->
                new RestApiException("Không tìm thấy màu với ID: " + id)
        );

        // Chuẩn hóa tên màu bằng cách loại bỏ khoảng trắng thừa
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên màu không được để trống!");
        }


        // Kiểm tra nếu tên đã tồn tại và không phải chính thực thể đang được cập nhật
        if (repository.existsByNameIgnoreCase(normalizedName) &&
                !oldColor.getName().equalsIgnoreCase(normalizedName)) {
            throw new RestApiException("Màu '" + normalizedName + "' đã tồn tại!");
        }

        // Cập nhật dữ liệu từ request
        oldColor.setName(normalizedName); // Cập nhật tên đã chuẩn hóa

        // Lưu và trả về đối tượng đã cập nhật
        return repository.save(oldColor);
    }

    @Override
    public Color delete(Long id) {
        Color color = this.getOne(id);
        color.setDeleted(!color.getDeleted());
        return repository.save(color);
    }
}
