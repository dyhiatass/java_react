package com.example.backend.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "messages") // Collection MongoDB pour stocker les messages
public class ChatMessage {

    @Id
    private String id;
    private String sender;  // Email de l'expéditeur
    private String receiver; // Email du destinataire (optionnel)
    private String content;
    private LocalDateTime timestamp; // Date et heure du message
}
