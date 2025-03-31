package com.ecoswap.ecoswap.messaging.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoswap.ecoswap.exchange.models.entities.Exchange;
import com.ecoswap.ecoswap.exchange.repositories.ExchangeRepository;
import com.ecoswap.ecoswap.messaging.models.ChatMessage;
import com.ecoswap.ecoswap.messaging.models.dto.ChatMessageDtoRequest;
import com.ecoswap.ecoswap.messaging.repositories.ChatMessageRepository;
import com.ecoswap.ecoswap.user.models.entities.User;
import com.ecoswap.ecoswap.user.repositories.UserRepository;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    public void saveMessage(ChatMessageDtoRequest message) {
        ChatMessage chatMessage = new ChatMessage();
        User sender = userRepository.findById(message.getSenderId()).get();
        User receiver = userRepository.findById(message.getReceiverId()).get();
        Exchange exchange = exchangeRepository.findById(message.getExchangeId()).get();
        chatMessage.setSender(sender);
        chatMessage.setReceiver(receiver);
        chatMessage.setContent(message.getContent());
        chatMessage.setExchange(exchange);
        chatMessage.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessagesBySenderAndReceiver(Long sender, Long receiver) {
        return chatMessageRepository.findMessagesBetweenUsers(sender, receiver);
    }


    public List<ChatMessage> getMessagesByExchangeId(Long receiverId) {
        return chatMessageRepository.findAllByReceiverIdOrderByTimestampDesc(receiverId);
    }
    

    

}