package com.fpt.niceshoes.service.impl;

import com.fpt.niceshoes.dto.response.NotificationResponse;
import com.fpt.niceshoes.entity.Notification;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.infrastructure.constant.NotificationType;
import com.fpt.niceshoes.repository.INotificationRepository;
import com.fpt.niceshoes.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private INotificationRepository notificationRepository;

    @Override
    public List<NotificationResponse> getByAccount(Long idAccount) {
        return notificationRepository.getByAccount(idAccount);
    }

    @Override
    public ResponseObject updateType(Long id) {
        Notification notification = notificationRepository.findById(id).get();
        notification.setType(NotificationType.DA_DOC);
        return new ResponseObject(notificationRepository.save(notification));
    }

    @Override
    public void delete(Long idNotification) {
        notificationRepository.deleteById(idNotification);
    }

    @Override
    public void deleteAllByAccount(Long idAccount) {
        notificationRepository.deleteAll(notificationRepository.findByAccountId(idAccount));
    }
}
