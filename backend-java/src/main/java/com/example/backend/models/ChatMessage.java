package com.example.backend.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessage {
    private String sender;
    private String content;
    private String timestamp;
}
