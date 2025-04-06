package com.ecoswap.ecoswap.messaging.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecoswap.ecoswap.messaging.models.ChatMessage;
import com.ecoswap.ecoswap.messaging.models.dto.ChatMessageDtoRequest;
import com.ecoswap.ecoswap.messaging.services.ChatService;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/message/create")
    public ResponseEntity<Void> saveMessage(@RequestBody ChatMessageDtoRequest chatMessage) {
        chatService.saveMessage(chatMessage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/message")
    public ResponseEntity<List<ChatMessage>> getMessagesBySenderAndReceiver(@RequestParam Long sender, 
        @RequestParam Long receiver) {
        return ResponseEntity.ok(chatService.getMessagesBySenderAndReceiver(sender, receiver));
    }

    @GetMapping("/message/exchange/{receiverId}")
    public ResponseEntity<List<ChatMessage>> getMessagesByExchangeId(@PathVariable Long receiverId) {
        return ResponseEntity.ok(chatService.getMessagesByExchangeId(receiverId));
    }
}
