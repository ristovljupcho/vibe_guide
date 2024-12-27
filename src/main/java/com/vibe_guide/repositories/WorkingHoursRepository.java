package com.vibe_guide.repositories;

import com.vibe_guide.entities.WorkingHours;
import com.vibe_guide.entities.composite_keys.WorkingHoursId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, WorkingHoursId> {
}
