package com.example.backend.services;

import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service pour gérer la logique métier des utilisateurs.
 * Il sert d'intermédiaire entre le contrôleur (UserController) et le repository (UserRepository).
 */
@Service // Indique que cette classe est un service Spring Boot
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName()); // Permet d'afficher des logs pour le suivi

    @Autowired // Injection automatique du repository pour accéder à la base de données
    private UserRepository userRepository;

    @Autowired // Injection automatique de l'encodeur de mot de passe pour la sécurité
    private PasswordEncoder passwordEncoder;

    /**
     * Récupère tous les utilisateurs enregistrés dans la base de données.
     *
     * @return une liste d'objets User.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Utilise le repository pour récupérer tous les utilisateurs
    }

    /**
     * Récupère un utilisateur en fonction de son adresse e-mail.
     *
     * @param email l'adresse e-mail de l'utilisateur recherché.
     * @return un Optional contenant l'utilisateur s'il existe, sinon un Optional vide.
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email); // Recherche de l'utilisateur dans la base de données
    }

    

    /**
     * Crée un nouvel utilisateur et l'ajoute à la base de données après vérification.
     *
     * @param user l'objet contenant les informations du nouvel utilisateur.
     * @return l'utilisateur enregistré avec son ID généré.
     */
    public User createUser(User user) {
        logger.info("Création d'un nouvel utilisateur avec email : " + user.getEmail());

        // 1️⃣ Vérifie si un utilisateur avec le même email existe déjà dans la base de données
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            logger.warning("L'utilisateur existe déjà !");
            throw new RuntimeException("Cet utilisateur existe déjà !");
        }

        // 2️⃣ Hachage du mot de passe avant stockage pour des raisons de sécurité
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Mot de passe haché pour l'utilisateur : " + user.getPassword());

        // 3️⃣ Enregistrement de l'utilisateur en base de données
        User savedUser = userRepository.save(user);
        logger.info("Utilisateur enregistré avec succès !");

        return savedUser;
    }

    /**
     * Supprime un utilisateur en fonction de son ID.
     *
     * @param id l'identifiant unique de l'utilisateur à supprimer.
     */
    public void deleteUser(String id) {
        logger.info("Suppression de l'utilisateur avec ID : " + id);
        userRepository.deleteById(id); // Supprime l'utilisateur de la base de données
    }
    public User updateUser(String id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(updatedUser.getUsername());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setPassword(updatedUser.getPassword());
                    existingUser.setRole(updatedUser.getRole());
                    existingUser.setBirthday(updatedUser.getBirthday());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
    }
    public List<User> getUserByBirthdate(String birthdate) {
    LocalDate date = LocalDate.parse(birthdate); // Convertit String → LocalDate
    return userRepository.findByBirthday(date);
}
}
