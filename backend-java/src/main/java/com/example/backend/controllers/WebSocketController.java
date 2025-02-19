package com.example.backend.controllers;

import com.example.backend.models.ChatMessage;
import com.example.backend.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage handleMessage(ChatMessage message) {
        logger.info("📩 Message reçu de {} à {} : {}", message.getSender(), message.getReceiver(), message.getContent());

        // 🔥 Enregistrer le message dans MongoDB
        ChatMessage savedMessage = chatService.saveMessage(message.getSender(), message.getReceiver(), message.getContent());

        return savedMessage;
    }
}
