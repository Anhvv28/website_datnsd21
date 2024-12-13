package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.dto.request.shoe.FindShoeReqeust;
import com.fpt.niceshoes.entity.Shoe;
import com.fpt.niceshoes.infrastructure.converter.ShoeConvert;
import com.fpt.niceshoes.infrastructure.exception.RestApiException;
import com.fpt.niceshoes.dto.request.shoe.ShoeRequest;
import com.fpt.niceshoes.dto.response.ShoeResponse;
import com.fpt.niceshoes.dto.response.promotion.ShoePromotionResponse;
import com.fpt.niceshoes.repository.IShoeDetailRepository;
import com.fpt.niceshoes.repository.IShoeRepository;
import com.fpt.niceshoes.service.ShoeService;
import com.fpt.niceshoes.infrastructure.common.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ShoeServiceImpl implements ShoeService {
    @Autowired
    private IShoeRepository shoeRepository;
    @Autowired
    private IShoeDetailRepository shoeDetailRepository;
    @Autowired
    private ShoeConvert shoeConvert;

    @Override
    public PageableObject<ShoeResponse> getAll(FindShoeReqeust request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSizePage());
        FindShoeReqeust customRequest = FindShoeReqeust.builder()
                .colors(request.getColor() != null ? Arrays.asList(request.getColor().split(",")) : null)
                .sizes(request.getSize() != null ? Arrays.asList(request.getSize().split(",")) : null)
                .soles(request.getSole() != null ? Arrays.asList(request.getSole().split(",")) : null)
                .categories(request.getCategory() != null ? Arrays.asList(request.getCategory().split(",")) : null)
                .brands(request.getBrand() != null ? Arrays.asList(request.getBrand().split(",")) : null)
                .size(request.getSize())
                .color(request.getColor())
                .sole(request.getSole())
                .name(request.getName())
                .brand(request.getBrand())
                .category(request.getCategory())
                .minPrice(request.getMinPrice())
                .maxPrice(request.getMaxPrice())
                .category(request.getCategory())
                .brand(request.getBrand())
                .status(request.getStatus())
                .build();
        // Fetching the data from the repository
        PageableObject<ShoeResponse> result = new PageableObject<>(shoeRepository.getAllShoe(customRequest, pageable));

        // Printing the result for debugging purposes
        System.out.println("Result Data: " + result);

        // Returning the data
        return result;
    }

    @Override
    public Shoe getOne(Long id) {
        return shoeRepository.findById(id).orElse(null);
    }

    @Override
    public Shoe create(ShoeRequest request) {
        // Chuẩn hóa tên: loại bỏ khoảng trắng ở đầu và cuối
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên sản phẩm không được để trống!");
        }


        // Kiểm tra nếu tên quá dài
        if (normalizedName.length() > 50) {
            throw new RestApiException("Tên sản phẩm quá dài, vui lòng thử lại!");
        }

        // Kiểm tra nếu tên sản phẩm đã tồn tại
        if (shoeRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new RestApiException(normalizedName + " đã tồn tại!");
        }

        // Chuyển đổi DTO thành thực thể Shoe và lưu
        Shoe shoe = shoeConvert.convertRequestToEntity(request);
        shoe.setName(normalizedName);  // Đảm bảo tên đã chuẩn hóa được lưu
        return shoeRepository.save(shoe);
    }

    @Override
    public Shoe update(Long id, ShoeRequest request) {
        Shoe oldShoe = shoeRepository.findById(id)
                .orElseThrow(() -> new RestApiException("Không tìm thấy sản phẩm với ID: " + id));

        // Chuẩn hóa tên: loại bỏ khoảng trắng ở đầu và cuối
        String normalizedName = request.getName().trim();

        // Kiểm tra nếu tên trống sau khi loại bỏ khoảng trắng
        if (normalizedName.isEmpty()) {
            throw new RestApiException("Tên sản phẩm không được để trống!");
        }
        // Kiểm tra nếu tên quá dài
        if (normalizedName.length() > 50) {
            throw new RestApiException("Tên sản phẩm có vẻ quá dài, vui lòng thử lại!");
        }

        // Kiểm tra nếu tên sản phẩm đã tồn tại và không phải chính thực thể đang cập nhật
        if (shoeRepository.existsByNameIgnoreCase(normalizedName) &&
                !oldShoe.getName().equalsIgnoreCase(normalizedName)) {
            throw new RestApiException(normalizedName + " đã tồn tại!");
        }

        // Cập nhật tên sản phẩm và lưu
        oldShoe.setName(normalizedName);
        return shoeRepository.save(oldShoe);
    }

    @Override
    public Shoe changeStatus(Long id) {
        Shoe shoe = shoeRepository.findById(id).get();
        shoe.setDeleted(shoe.getDeleted() == false ? true : false);
        shoeRepository.save(shoe);
        shoeDetailRepository.findByShoe(shoe).forEach(shoeDetail -> {
            shoeDetail.setDeleted(shoeDetail.getDeleted() == false ? true : false);
            shoeDetailRepository.save(shoeDetail);
        });
        return shoe;
    }

    @Override
    public List<ShoePromotionResponse> getAllShoeInPromotion(Long promotion) {
        return shoeRepository.getAllShoeInPromotion(promotion);
    }

    @Override
    public List<ShoeResponse> getTopSell(Integer top) {
        return shoeRepository.topSell(top);
    }
}
