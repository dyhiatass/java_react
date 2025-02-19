package com.example.backend.services;

import com.example.backend.models.ChatMessage;
import com.example.backend.repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    /**
     * Sauvegarde un message dans MongoDB.
     */
    public ChatMessage saveMessage(String sender, String receiver, String content) {
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    /**
     * Récupère tous les messages envoyés par un utilisateur spécifique.
     */
    public List<ChatMessage> getMessagesBySender(String sender) {
        return chatMessageRepository.findBySender(sender);
    }

    /**
     * Récupère tous les messages reçus par un utilisateur spécifique.
     */
    public List<ChatMessage> getMessagesByReceiver(String receiver) {
        return chatMessageRepository.findByReceiver(receiver);
    }
}
