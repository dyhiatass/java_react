package com.example.backend.controllers;

import com.example.backend.models.ChatMessage;
import com.example.backend.services.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages") // ✅ Diffuse à tous les abonnés STOMP
    public ChatMessage sendMessage(ChatMessage message) {
        ChatMessage savedMessage = chatService.saveMessage(
                message.getSender(),
                message.getReceiver(),
                message.getContent()
        );

        // ✅ Envoi du message privé au destinataire uniquement
        messagingTemplate.convertAndSendToUser(
                message.getReceiver(),
                "/queue/messages",
                savedMessage
        );

        return savedMessage;
    }
}
