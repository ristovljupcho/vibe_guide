package com.vibe_guide.services;

import com.vibe_guide.dtos.WorkingHoursMissingDaysResponseDTO;
import com.vibe_guide.dtos.WorkingHoursResponseDTO;

import java.util.List;
import java.util.UUID;

public interface WorkingHoursQueryService {
    List<WorkingHoursResponseDTO> getWeeklyWorkingHoursForPlace(UUID placeId);

    List<WorkingHoursMissingDaysResponseDTO> getMissingWorkingHoursDaysForPlace(UUID placeId);
}