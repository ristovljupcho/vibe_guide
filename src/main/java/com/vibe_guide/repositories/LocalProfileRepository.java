package com.vibe_guide.repositories;

import com.vibe_guide.entities.LocalProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocalProfileRepository extends JpaRepository<LocalProfile, UUID> {
}
