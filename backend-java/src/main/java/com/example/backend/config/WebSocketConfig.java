package com.example.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        logger.info("📡 WebSocket STOMP Endpoint enregistré sur /ws-chat");
        registry.addEndpoint("/ws-chat").setAllowedOrigins("*"); // Suppression de SockJS
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        logger.info("📨 Configuration du message broker :");
        logger.info("➡️ Destination préfixe pour les clients : /app");
        logger.info("⬅️ Topics activés pour les abonnés : /topic");
        registry.enableSimpleBroker("/topic"); // Broker pour envoyer des messages
        registry.setApplicationDestinationPrefixes("/app"); // Préfixe des messages entrants
    }
}
