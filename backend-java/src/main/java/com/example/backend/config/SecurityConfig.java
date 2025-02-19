package com.example.backend.config;

import com.example.backend.security.WebSocketSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final WebSocketSecurityInterceptor webSocketSecurityInterceptor;

    public SecurityConfig(WebSocketSecurityInterceptor webSocketSecurityInterceptor) {
        this.webSocketSecurityInterceptor = webSocketSecurityInterceptor;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users").permitAll()  // Permettre l'inscription
                .requestMatchers("/auth/login").permitAll()  // Permettre le login
                .requestMatchers("/ws-chat/**").authenticated() // Sécuriser WebSocket
                .requestMatchers("/users/**").authenticated()  // Protéger les autres routes
            )
            .httpBasic().disable();  // Désactiver l'authentification basique

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            throw new RuntimeException("Spring Security par défaut désactivé !");
        };
    }

    // Sécurité WebSocket sans `ChannelSecurityInterceptor`
    public void customizeClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketSecurityInterceptor);
    }
}
