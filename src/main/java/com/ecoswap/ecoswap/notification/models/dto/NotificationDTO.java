package com.ecoswap.ecoswap.notification.models.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long id;          // ID de la notificación
    private Long receiverId;  // ID del usuario que recibe la notificación
    private String message;   // Mensaje de la notificación
    private boolean isRead;   // Estado de lectura
    private LocalDateTime createdAt; // Fecha de creación
}
