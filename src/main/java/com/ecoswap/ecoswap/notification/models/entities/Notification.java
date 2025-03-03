package com.ecoswap.ecoswap.notification.models.entities;

import com.ecoswap.ecoswap.user.models.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User receiver;

    private String message;
    private boolean isRead = false;
    private LocalDateTime createdAt = LocalDateTime.now();

}
