package com.vibe_guide.workinghours.repositories;

import com.vibe_guide.workinghours.entities.WorkingHours;
import com.vibe_guide.workinghours.entities.WorkingHoursId;
import com.vibe_guide.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, WorkingHoursId> {
    List<WorkingHours> findAllByPlaceId(UUID placeId);
    Optional<WorkingHours> findByPlaceIdAndDayOfWeek(UUID placeId, DayOfWeek dayOfWeek);
}