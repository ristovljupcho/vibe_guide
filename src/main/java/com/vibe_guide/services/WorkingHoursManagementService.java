package com.vibe_guide.services;

import com.vibe_guide.dtos.WorkingHoursDeleteRequestDTO;
import com.vibe_guide.dtos.WorkingHoursRequestDTO;

import java.util.List;
import java.util.UUID;

public interface WorkingHoursManagementService {
    String insertWorkingHoursForPlace(UUID placeId, List<WorkingHoursRequestDTO> workingHoursRequestDTOs);

    String updateDailyWorkingHoursForPlace(UUID placeId, WorkingHoursRequestDTO workingHoursRequestDTO);

    String deleteWorkingHoursForPlace(UUID placeId, WorkingHoursDeleteRequestDTO workingHoursDeleteRequestDTO);
}
