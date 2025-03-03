package com.ecoswap.ecoswap.notification.services;

import com.ecoswap.ecoswap.notification.models.dto.NotificationDTO;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import com.ecoswap.ecoswap.user.models.entities.User;

import java.util.List;

public interface NotificationService {
    void sendNotification(UserDTO receiver, String message);
    List<NotificationDTO> getUnreadNotifications(Long userId);
    void markAsRead(Long notificationId);


}
