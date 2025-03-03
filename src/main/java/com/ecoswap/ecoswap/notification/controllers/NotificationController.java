package com.ecoswap.ecoswap.notification.controllers;

import com.ecoswap.ecoswap.notification.models.dto.NotificationDTO;
import com.ecoswap.ecoswap.notification.services.NotificationService;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Para enviar WebSockets manualmente

    // 🔹 Enviar una notificación (REST API + WebSockets)
    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationDTO notificationDTO) {
        // Crear un objeto UserDTO con el ID del receptor
        UserDTO receiver = new UserDTO();
        receiver.setId(notificationDTO.getReceiverId());

        // Enviar la notificación a través del servicio
        notificationService.sendNotification(receiver, notificationDTO.getMessage());

    }

    // 🔹 Obtener notificaciones no leídas de un usuario
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(@PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // 🔹 Marcar una notificación como leída
    @PutMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
