package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.entity.Brand;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import com.fpt.niceshoes.infrastructure.converter.BrandConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.dto.request.properties.BrandRequest;
import com.fpt.niceshoes.dto.response.BrandResponse;
import com.fpt.niceshoes.repository.IBrandRepository;
import com.fpt.niceshoes.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private IBrandRepository repository;
    @Autowired
    private BrandConvert brandConvert;

    @Override
    public PageableObject<BrandResponse> getAll(BrandRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        return new PageableObject<>(repository.getAllBrand(request, pageable));
    }

    @Override
    public Brand getOne(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Brand create(BrandRequest request) {
        // Chuẩn hóa tên thương hiệu: loại bỏ khoảng trắng ở đầu và cuối
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên thương hiệu không được để trống!");
        }
        // Kiểm tra nếu tên không phải là chữ và không chứa ký tự đặc biệt
        if (!normalizedName.matches("[a-zA-Z\\s]+")) {
            throw new RestApiException("Tên thương hiệu chỉ được chứa chữ!");
        }

        // Kiểm tra nếu thương hiệu đã tồn tại
        if (repository.existsByNameIgnoreCase(normalizedName)) {
            throw new RestApiException("Thương hiệu '" + normalizedName + "' đã tồn tại!");
        }

        // Chuyển đổi DTO thành thực thể Brand và lưu
        Brand brand = brandConvert.convertRequestToEntity(request);
        brand.setName(normalizedName); // Đảm bảo lưu tên đã chuẩn hóa
        return repository.save(brand);
    }

    @Override
    public Brand update(Long id, BrandRequest request) {
        Brand oldBrand = repository.findById(id)
                .orElseThrow(() -> new RestApiException("Không tìm thấy thương hiệu với ID: " + id));

        // Chuẩn hóa tên thương hiệu
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên thương hiệu không được để trống!");
        }
        // Kiểm tra nếu tên không phải là chữ và không chứa ký tự đặc biệt
        if (!normalizedName.matches("[a-zA-Z\\s]+")) {
            throw new RestApiException("Tên thương hiệu chỉ được chứa chữ!");
        }

        // Kiểm tra nếu thương hiệu đã tồn tại và không phải chính thực thể đang cập nhật
        if (repository.existsByNameIgnoreCase(normalizedName) &&
                !oldBrand.getName().equalsIgnoreCase(normalizedName)) {
            throw new RestApiException("Thương hiệu '" + normalizedName + "' đã tồn tại!");
        }

        // Cập nhật tên thương hiệu và lưu
        oldBrand.setName(normalizedName);
        return repository.save(oldBrand);
    }

    @Override
    public Brand delete(Long id) {
        Brand brand = this.getOne(id);
        brand.setDeleted(!brand.getDeleted());
        return repository.save(brand);
    }
}
