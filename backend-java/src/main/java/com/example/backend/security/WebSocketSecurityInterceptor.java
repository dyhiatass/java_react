package com.example.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

@Component
public class WebSocketSecurityInterceptor implements ChannelInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketSecurityInterceptor.class);
    private final String secret = "mySecretKeymySecretKeymySecretKeymySecretKey"; // Même clé que JwtUtil
    private final Key secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        List<String> authorization = accessor.getNativeHeader("Authorization");
        if (authorization != null && !authorization.isEmpty()) {
            String token = authorization.get(0).replace("Bearer ", "");

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.getSubject();
                logger.info("🔐 WebSocket JWT Auth OK pour : " + email);

                // Ajoute l'email en tant qu'attribut pour l'utiliser plus tard
                accessor.setUser(() -> email);
            } catch (Exception e) {
                logger.error("❌ Token JWT invalide pour WebSocket !");
                throw new SecurityException("Token JWT invalide");
            }
        } else {
            logger.warn("⚠️ Aucun token JWT fourni pour WebSocket !");
            throw new SecurityException("Token JWT manquant");
        }

        return message;
    }
}
