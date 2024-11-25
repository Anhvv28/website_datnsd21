package com.fpt.niceshoes.service;

import com.fpt.niceshoes.dto.response.NotificationResponse;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getByAccount(Long idAccount);
    ResponseObject updateType(Long id);
    void delete(Long idNotification);
    void deleteAllByAccount(Long idAccount);
}
