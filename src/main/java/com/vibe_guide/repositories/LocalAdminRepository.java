package com.vibe_guide.repositories;

import com.vibe_guide.entities.LocalAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocalAdminRepository extends JpaRepository<LocalAdmin, UUID> {
}
