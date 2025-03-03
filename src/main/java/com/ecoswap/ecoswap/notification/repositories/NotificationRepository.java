package com.ecoswap.ecoswap.notification.repositories;

import com.ecoswap.ecoswap.notification.models.entities.Notification;
import com.ecoswap.ecoswap.user.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverIdAndIsReadFalse(Long receiverId);
}
