package com.vibe_guide.services;

import com.vibe_guide.dtos.WorkingHoursChangeTimeRequestDTO;
import com.vibe_guide.dtos.WorkingHoursRequestDTO;
import com.vibe_guide.enums.DayOfWeek;

import java.util.List;
import java.util.UUID;

public interface WorkingHoursManagementService {
    String insertWeeklyWorkingHoursForPlace(UUID placeId, List<WorkingHoursRequestDTO> workingHoursRequestDTOs);
    String updateDailyWorkingHoursForPlace(UUID placeId, DayOfWeek dayOfWeek, WorkingHoursChangeTimeRequestDTO workingHoursChangeTimeRequestDTO);
}
