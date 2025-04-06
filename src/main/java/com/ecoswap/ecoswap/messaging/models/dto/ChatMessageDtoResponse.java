package com.ecoswap.ecoswap.messaging.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDtoResponse {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private String timestamp;
    private Long exchangeId;

}
