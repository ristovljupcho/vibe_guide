package com.vibe_guide.domain.workinghours.services;

import com.vibe_guide.domain.workinghours.dtos.WorkingHoursDeleteRequestDTO;
import com.vibe_guide.domain.workinghours.dtos.WorkingHoursRequestDTO;
import java.util.List;
import java.util.UUID;

public interface WorkingHoursManagementService {
  String insert(UUID placeId, List<WorkingHoursRequestDTO> workingHoursRequestDTOs);

  String update(UUID placeId, WorkingHoursRequestDTO workingHoursRequestDTO);

  String delete(UUID placeId, WorkingHoursDeleteRequestDTO workingHoursDeleteRequestDTO);
}

