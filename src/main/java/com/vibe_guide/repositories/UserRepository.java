package com.vibe_guide.repositories;

import com.vibe_guide.entities.User;
import com.vibe_guide.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(" select user " +
            "from User user")
    Page<User> getPaginatedUsers(Pageable pageable);

    @Query("select user" +
            " from User user" +
            " where user.id = :userId")
    User getUserById(@Param("userId") UUID userid);

    @Query("select user " +
            "from User user " +
            "where user.role = : userRole")
    Page<User> getPaginatedUsersByRole(@Param("userRole") Role userRole, Pageable pageable);

    @Query(" select user " +
            "from User user" +
            " where user.username = :username")
    Page<User> getPaginatedUsersByUsername(@Param("username") String username, Pageable pageable);
}

