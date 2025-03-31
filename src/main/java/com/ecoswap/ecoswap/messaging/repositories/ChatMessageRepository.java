package com.ecoswap.ecoswap.messaging.repositories;

import com.ecoswap.ecoswap.messaging.models.ChatMessage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllBySender_IdAndReceiver_Id(Long senderId, Long receiverId);
    List<ChatMessage> findAllBySenderIdOrReceiverId(Long senderId, Long receiverId);
    List<ChatMessage> findAllByReceiverIdOrderByTimestampDesc(Long receiverId);

    @Query("SELECT m FROM ChatMessage m WHERE " +
           "(m.sender.id = :userId AND m.receiver.id = :otherUserId) OR " +
           "(m.sender.id = :otherUserId AND m.receiver.id = :userId) " +
           "ORDER BY m.timestamp ASC")
    List<ChatMessage> findMessagesBetweenUsers(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);
    
}