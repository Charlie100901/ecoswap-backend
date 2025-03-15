package com.ecoswap.ecoswap.notification.services.impl;

import com.ecoswap.ecoswap.notification.models.dto.NotificationDTO;
import com.ecoswap.ecoswap.notification.models.entities.Notification;
import com.ecoswap.ecoswap.notification.repositories.NotificationRepository;
import com.ecoswap.ecoswap.notification.services.NotificationService;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import com.ecoswap.ecoswap.user.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;  // WebSockets

    @Override
    public void sendNotification(UserDTO receiver, String message) {
        User user = new User();
        user.setId(receiver.getId());
        user.setName(receiver.getName());
        user.setAddress(receiver.getAddress());
        user.setEmail(receiver.getEmail());
        user.setCellphoneNumber(receiver.getCellphoneNumber());

        Notification notification = new Notification();
        notification.setReceiver(user);
        notification.setMessage(message);
        notification.setRead(false);

        notificationRepository.save(notification);

        NotificationDTO notificationDTO = new NotificationDTO(notification.getId(), notification.getReceiver().getId(), message, false, notification.getCreatedAt());
        messagingTemplate.convertAndSend("/topic/notifications/" + receiver.getId(), notificationDTO);
        System.out.println("📤 Mensaje enviado a /topic/notifications: " + message);
    }

    @Override
    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByReceiverIdAndIsReadFalse(userId);
        return notifications.stream()
                .map(notification -> new NotificationDTO(
                        notification.getId(),
                        notification.getReceiver().getId(),
                        notification.getMessage(),
                        notification.isRead(),
                        notification.getCreatedAt()
                ))
                .toList();
    }


    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
}
