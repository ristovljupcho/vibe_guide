package com.vibe_guide.repositories;

import com.vibe_guide.entities.PlaceAdmin;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.composite_keys.PlaceAdminId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PlaceAdminRepository extends JpaRepository<PlaceAdmin, PlaceAdminId> {
    @Query("SELECT pa " +
            "FROM PlaceAdmin pa " +
            "WHERE pa.id.placeId = :placeId")
    List<User> findAllByPlaceId(UUID placeId);
}