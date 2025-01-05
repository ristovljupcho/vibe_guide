package com.vibe_guide.repositories;

import com.vibe_guide.entities.LocalProfileAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocalAdminRepository extends JpaRepository<LocalProfileAdmin, UUID> {
}
