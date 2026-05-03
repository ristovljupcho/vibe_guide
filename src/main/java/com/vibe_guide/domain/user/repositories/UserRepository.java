package com.vibe_guide.domain.user.repositories;

import com.vibe_guide.domain.user.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);
}
