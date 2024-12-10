package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.entity.Sole;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.converter.SoleConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.dto.request.properties.SoleRequest;
import com.fpt.niceshoes.dto.response.SoleResponse;
import com.fpt.niceshoes.repository.ISoleRepository;
import com.fpt.niceshoes.service.SoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SoleServiceImpl implements SoleService {
    @Autowired
    private ISoleRepository repository;
    @Autowired
    private SoleConvert soleConvert;


    @Override
    public PageableObject<SoleResponse> getAll(SoleRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PageableObject<>(repository.getAllSole(request, pageable));
    }

    @Override
    public Sole getOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Sole create(SoleRequest request) {
        // Chuẩn hóa tên đế giày: loại bỏ khoảng trắng ở đầu và cuối
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên đế giày không được để trống!");
        }

        // Kiểm tra nếu đế giày đã tồn tại
        if (repository.existsByNameIgnoreCase(normalizedName)) {
            throw new RestApiException("Đế giày '" + normalizedName + "' đã tồn tại!");
        }

        // Chuyển đổi DTO thành thực thể Sole và lưu
        Sole sole = soleConvert.convertRequestToEntity(request);
        sole.setName(normalizedName); // Đảm bảo lưu tên đã chuẩn hóa
        return repository.save(sole);
    }

    @Override
    public Sole update(Long id, SoleRequest request) {
        Sole oldSole = repository.findById(id)
                .orElseThrow(() -> new RestApiException("Không tìm thấy đế giày với ID: " + id));

        // Chuẩn hóa tên đế giày
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên đế giày không được để trống!");
        }


        // Kiểm tra nếu đế giày đã tồn tại và không phải chính thực thể đang cập nhật
        if (repository.existsByNameIgnoreCase(normalizedName) &&
                !oldSole.getName().equalsIgnoreCase(normalizedName)) {
            throw new RestApiException("Đế giày '" + normalizedName + "' đã tồn tại!");
        }

        // Cập nhật tên đế giày và lưu
        oldSole.setName(normalizedName);
        return repository.save(oldSole);
    }

    @Override
    public Sole delete(Long id) {
        Sole sole = this.getOne(id);
        sole.setDeleted(!sole.getDeleted());
        return repository.save(sole);
    }
}
