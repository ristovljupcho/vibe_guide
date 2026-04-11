package com.vibe_guide.workinghours.services;

import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.WorkingHoursAlreadyPresentException;
import com.vibe_guide.exceptions.WorkingHoursNotFoundException;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.workinghours.dtos.WorkingHoursDeleteRequestDTO;
import com.vibe_guide.workinghours.dtos.WorkingHoursRequestDTO;
import com.vibe_guide.workinghours.entities.WorkingHours;
import com.vibe_guide.workinghours.entities.WorkingHoursId;
import com.vibe_guide.workinghours.repositories.WorkingHoursRepository;
import com.vibe_guide.workinghours.utils.WorkingHoursResponseMessages;
import jakarta.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WorkingHoursManagementServiceImpl implements WorkingHoursManagementService {

  private final WorkingHoursRepository workingHoursRepository;
  private final PlaceRepository placeRepository;

  @Transactional
  @Override
  public String insert(UUID placeId, List<WorkingHoursRequestDTO> workingHoursRequestDTOs) {
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    List<DayOfWeek> alreadyInsertedWorkingHours =
        workingHoursRepository.findAllByPlaceId(placeId).stream()
            .map(WorkingHours::getDayOfWeek)
            .toList();
    List<DayOfWeek> requestedWorkingHours =
        workingHoursRequestDTOs.stream().map(WorkingHoursRequestDTO::dayOfWeek).toList();
    List<DayOfWeek> duplicateDays =
        requestedWorkingHours.stream().filter(alreadyInsertedWorkingHours::contains).toList();
    if (!duplicateDays.isEmpty()) {
      throw new WorkingHoursAlreadyPresentException(placeId, duplicateDays);
    }

    List<WorkingHours> workingHoursList = new ArrayList<>();
    for (WorkingHoursRequestDTO dto : workingHoursRequestDTOs) {
      DayOfWeek dayOfWeek = dto.dayOfWeek();
      LocalTime startTime = dto.startTime();
      LocalTime endTime = dto.endTime();

      WorkingHours workingHours = new WorkingHours();
      workingHours.setPlace(place);
      workingHours.setDayOfWeek(dayOfWeek);
      workingHours.setStartTime(startTime);
      workingHours.setEndTime(endTime);

      workingHoursList.add(workingHours);
    }

    workingHoursRepository.saveAll(workingHoursList);

    String requestedWorkingHoursStringFormat =
        requestedWorkingHours.stream().map(DayOfWeek::toString).collect(Collectors.joining(", "));

    return String.format(
        WorkingHoursResponseMessages.WORKING_HOURS_INSERT_MESSAGE,
        requestedWorkingHoursStringFormat,
        placeId);
  }

  @Transactional
  @Override
  public String update(UUID placeId, WorkingHoursRequestDTO workingHoursRequestDTO) {
    checkIfPlaceExists(placeId);
    DayOfWeek dayOfWeek = workingHoursRequestDTO.dayOfWeek();
    WorkingHours workingHours =
        workingHoursRepository
            .findByPlaceIdAndDayOfWeek(placeId, dayOfWeek)
            .orElseThrow(() -> new WorkingHoursNotFoundException(placeId, dayOfWeek));

    LocalTime startTime = workingHoursRequestDTO.startTime();
    LocalTime endTime = workingHoursRequestDTO.endTime();
    workingHours.setStartTime(startTime);
    workingHours.setEndTime(endTime);

    workingHoursRepository.save(workingHours);

    return String.format(
        WorkingHoursResponseMessages.WORKING_HOURS_UPDATE_MESSAGE, dayOfWeek, placeId);
  }

  @Override
  public String delete(UUID placeId, WorkingHoursDeleteRequestDTO workingHoursDeleteRequestDTO) {
    checkIfPlaceExists(placeId);
    List<DayOfWeek> daysToDelete = workingHoursDeleteRequestDTO.daysToDelete();
    List<DayOfWeek> currentWorkingDays =
        workingHoursRepository.findAllByPlaceId(placeId).stream()
            .map(WorkingHours::getDayOfWeek)
            .toList();

    List<DayOfWeek> missingDays =
        daysToDelete.stream().filter(day -> !currentWorkingDays.contains(day)).toList();
    if (!missingDays.isEmpty()) {
      String missingDaysStringFormat =
          missingDays.stream().map(DayOfWeek::toString).collect(Collectors.joining(", "));
      throw new WorkingHoursNotFoundException(placeId, missingDaysStringFormat);
    }

    List<WorkingHoursId> workingHoursIds =
        daysToDelete.stream().map(day -> new WorkingHoursId(day, placeId)).toList();
    workingHoursRepository.deleteAllById(workingHoursIds);

    String daysToDeleteStringFormat =
        daysToDelete.stream().map(DayOfWeek::toString).collect(Collectors.joining(", "));

    return String.format(
        WorkingHoursResponseMessages.WORKING_HOURS_DELETE_MESSAGE,
        daysToDeleteStringFormat,
        placeId);
  }

  private void checkIfPlaceExists(UUID placeId) {
    if (!placeRepository.existsById(placeId)) {
      throw new PlaceNotFoundException(placeId);
    }
  }
}
