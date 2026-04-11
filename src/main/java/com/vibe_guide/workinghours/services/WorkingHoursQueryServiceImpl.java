package com.vibe_guide.workinghours.services;

import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.workinghours.dtos.WorkingHoursMissingDaysResponseDTO;
import com.vibe_guide.workinghours.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.workinghours.entities.WorkingHours;
import com.vibe_guide.workinghours.mappers.WorkingHoursMapper;
import com.vibe_guide.workinghours.repositories.WorkingHoursRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WorkingHoursQueryServiceImpl implements WorkingHoursQueryService {

  WorkingHoursRepository workingHoursRepository;
  PlaceRepository placeRepository;
  WorkingHoursMapper workingHoursMapper;

  @Override
  public List<WorkingHoursResponseDTO> getWeeklyWorkingHoursForPlace(UUID placeId) {
    checkIfPlaceExists(placeId);

    List<WorkingHours> weeklyWorkingHours = workingHoursRepository.findAllByPlaceId(placeId);

    return weeklyWorkingHours.stream().map(workingHoursMapper::toWorkingHoursResponseDTO).toList();
  }

  @Override
  public List<WorkingHoursMissingDaysResponseDTO> getMissingWorkingHoursDaysForPlace(UUID placeId) {
    checkIfPlaceExists(placeId);

    Set<DayOfWeek> existingDays =
        workingHoursRepository.findAllByPlaceId(placeId).stream()
            .map(WorkingHours::getDayOfWeek)
            .collect(Collectors.toSet());
    Stream<DayOfWeek> missingDays =
        Arrays.stream(DayOfWeek.values()).filter(day -> !existingDays.contains(day));

    return missingDays.map(workingHoursMapper::toWorkingHoursMissingDaysResponseDTO).toList();
  }

  private void checkIfPlaceExists(UUID placeId) {
    if (!placeRepository.existsById(placeId)) throw new PlaceNotFoundException(placeId);
  }
}
