package com.example.backend.repositories;

import com.example.backend.models.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findBySender(String sender);
    List<ChatMessage> findByReceiver(String receiver);
    List<ChatMessage> findBySenderAndReceiver(String sender, String receiver);
}
