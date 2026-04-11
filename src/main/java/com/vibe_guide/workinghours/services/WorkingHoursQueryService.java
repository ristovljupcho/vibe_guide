package com.vibe_guide.workinghours.services;

import com.vibe_guide.workinghours.dtos.WorkingHoursMissingDaysResponseDTO;
import com.vibe_guide.workinghours.dtos.WorkingHoursResponseDTO;
import java.util.List;
import java.util.UUID;

public interface WorkingHoursQueryService {
  List<WorkingHoursResponseDTO> getAllByPlaceId(UUID placeId);

  List<WorkingHoursMissingDaysResponseDTO> getMissingDaysByPlaceId(UUID placeId);
}
