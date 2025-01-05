package com.vibe_guide.repositories;

import com.vibe_guide.entities.PlaceAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceAdminRepository extends JpaRepository<PlaceAdmin, UUID> {
}
