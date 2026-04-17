package com.vibe_guide.domain.workinghours.repositories;

import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.domain.workinghours.entities.WorkingHours;
import com.vibe_guide.domain.workinghours.entities.WorkingHoursId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, WorkingHoursId> {
  List<WorkingHours> findAllByPlaceId(UUID placeId);

  Optional<WorkingHours> findByPlaceIdAndDayOfWeek(UUID placeId, DayOfWeek dayOfWeek);
}

