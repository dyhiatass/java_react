package com.example.backend.repositories;

import com.example.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List; // Pour gérer les listes
import java.time.LocalDate; // Pour utiliser LocalDate

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

   /**
    * Recherche d'utilisateur par date de naissance
    * 
    * @param birthday la date de naissance recheché
    * @return la liste des utilisateurs qui corréspondent à cette date de naissance
    * */
   List<User> findByBirthday(LocalDate birthday); 
}
