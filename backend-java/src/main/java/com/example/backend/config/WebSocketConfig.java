package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // ✅ Supporte messages publics & privés
        config.setApplicationDestinationPrefixes("/app"); // ✅ Préfixe pour les requêtes STOMP
        config.setUserDestinationPrefix("/user"); // ✅ Gestion des messages privés
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat") // ✅ Point d'accès WebSocket
                .setAllowedOrigins("*") // ✅ Accepte toutes les origines
                .withSockJS(); // ✅ Active SockJS pour compatibilité navigateurs
    }
}
