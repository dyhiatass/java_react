package com.example.backend.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate; // Pour gérer les dates

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "users") // Indique que cette classe représente une collection MongoDB
public class User {

    @Id
    private String id;
    private LocalDate birthday;
    private String username;
    private String email;
    private String password;
    private String role; // "USER" ou "ADMIN"
}
