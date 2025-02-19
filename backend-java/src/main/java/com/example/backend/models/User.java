package com.example.backend.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users") // Spécifie la collection MongoDB
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;
    private String role;
    private LocalDate birthday;
}
