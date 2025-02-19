package com.example.backend.controllers;

import com.example.backend.models.ChatMessage;
import com.example.backend.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Récupérer les messages envoyés par un utilisateur
    @GetMapping("/messages/sent/{sender}")
    public List<ChatMessage> getMessagesBySender(@PathVariable String sender) {
        return chatService.getMessagesBySender(sender);
    }

    // Récupérer les messages reçus par un utilisateur
    @GetMapping("/messages/received/{receiver}")
    public List<ChatMessage> getMessagesByReceiver(@PathVariable String receiver) {
        return chatService.getMessagesByReceiver(receiver);
    }
}
