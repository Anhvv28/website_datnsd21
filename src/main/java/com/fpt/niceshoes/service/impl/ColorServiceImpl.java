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
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new RestApiException("Màu " + request.getName() + " đã tồn tại!");
        }
        Color color = colorConvert.convertRequestToEntity(request);
        return repository.save(color);
    }

    @Override
    public Color update(Long id, ColorRequest request) {
        Color oldColor = repository.findById(id).get();
        if (repository.existsByNameIgnoreCase(request.getName())) {
            if (oldColor.getName().equals(request.getName())) {
                return repository.save(colorConvert.convertRequestToEntity(oldColor, request));
            }
            throw new RestApiException("Màu " + request.getName() + " đã tồn tại!");
        } else {
            return repository.save(colorConvert.convertRequestToEntity(oldColor, request));
        }
    }

    @Override
    public Color delete(Long id) {
        Color color = this.getOne(id);
        color.setDeleted(!color.getDeleted());
        return repository.save(color);
    }
}
