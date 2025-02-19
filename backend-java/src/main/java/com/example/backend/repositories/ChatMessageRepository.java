package com.example.backend.repositories;

import com.example.backend.models.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findBySender(String sender); // Récupérer les messages envoyés par un utilisateur
    List<ChatMessage> findByReceiver(String receiver); // Récupérer les messages reçus par un utilisateur
}
