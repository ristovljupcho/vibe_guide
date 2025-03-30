package com.vibe_guide.repositories;

import com.vibe_guide.entities.WorkingHours;
import com.vibe_guide.entities.composite_keys.WorkingHoursId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, WorkingHoursId> {
    List<WorkingHours> findAllByPlaceId(UUID placeId);
}