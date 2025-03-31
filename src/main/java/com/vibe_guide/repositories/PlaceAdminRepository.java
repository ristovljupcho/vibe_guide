package com.vibe_guide.repositories;

import com.vibe_guide.entities.PlaceAdmin;
import com.vibe_guide.entities.composite_keys.PlaceAdminId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceAdminRepository extends JpaRepository<PlaceAdmin, PlaceAdminId> {
}
