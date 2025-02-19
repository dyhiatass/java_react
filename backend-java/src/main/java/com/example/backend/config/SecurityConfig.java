package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ❌ Désactiver CSRF pour WebSockets
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/socket.io/**").permitAll()
                        .requestMatchers("/chat/**").permitAll()
                        .requestMatchers("/ws-chat/**").permitAll() // ✅ Autoriser WebSockets STOMP
                        .requestMatchers("/topic/**").permitAll() // ✅ Autoriser les messages STOMP
                        .requestMatchers("/app/**").permitAll() // ✅ Autoriser les destinations STOMP
                        .requestMatchers("/users/**").authenticated()
                )
                .httpBasic().disable();

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
}
