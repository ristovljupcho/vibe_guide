package com.vibe_guide.domain.placeadmin.repositories;

import com.vibe_guide.domain.placeadmin.entities.PlaceAdmin;
import com.vibe_guide.domain.placeadmin.entities.PlaceAdminId;
import com.vibe_guide.domain.user.entities.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaceAdminRepository extends JpaRepository<PlaceAdmin, PlaceAdminId> {
  @Query("SELECT pa.user " + "FROM PlaceAdmin pa " + "WHERE pa.id.placeId = :placeId")
  List<User> findAllByPlaceId(UUID placeId);
}

