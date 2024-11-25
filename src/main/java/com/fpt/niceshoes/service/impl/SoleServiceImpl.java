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
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new RestApiException("Đế giày " + request.getName() + " đã tồn tại!");
        }
        Sole sole = soleConvert.convertRequestToEntity(request);
        return repository.save(sole);
    }

    @Override
    public Sole update(Long id, SoleRequest request) {
        Sole oldSole = repository.findById(id).get();
        if (repository.existsByNameIgnoreCase(request.getName())) {
            if (oldSole.getName().equals(request.getName())) {
                return repository.save(soleConvert.convertRequestToEntity(oldSole, request));
            }
            throw new RestApiException("Đế giày " + request.getName() + " đã tồn tại!");
        } else {
            return repository.save(soleConvert.convertRequestToEntity(oldSole, request));
        }
    }

    @Override
    public Sole delete(Long id) {
        Sole sole = this.getOne(id);
        sole.setDeleted(!sole.getDeleted());
        return repository.save(sole);
    }
}
