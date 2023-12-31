package main.repository;

import main.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
