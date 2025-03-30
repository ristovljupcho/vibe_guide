package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.WorkingHoursChangeTimeRequestDTO;
import com.vibe_guide.dtos.WorkingHoursRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.WorkingHours;
import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.WorkingHoursAlreadyPresentException;
import com.vibe_guide.exceptions.WorkingHoursNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.WorkingHoursRepository;
import com.vibe_guide.services.WorkingHoursManagementService;
import com.vibe_guide.utils.WorkingHoursResponseMessages;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class WorkingHoursManagementServiceImpl implements WorkingHoursManagementService {

    private final WorkingHoursRepository workingHoursRepository;
    private final PlaceRepository placeRepository;

    /**
     * Inserts multiple {@link WorkingHours} objects for {@link Place} with provided ID.
     *
     * @param placeId                 ID of the {@link Place}.
     * @param workingHoursRequestDTOs List of {@link WorkingHoursRequestDTO}.
     * @return Response message of type {@link WorkingHoursResponseMessages}.
     */
    @Transactional
    @Override
    public String insertWeeklyWorkingHoursForPlace(UUID placeId, List<WorkingHoursRequestDTO> workingHoursRequestDTOs) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
        List<DayOfWeek> alreadyInsertedWorkingHours =
                workingHoursRepository.findAllByPlaceId(placeId).stream().map(WorkingHours::getDayOfWeek).toList();
        List<DayOfWeek> requestedWorkingHours =
                workingHoursRequestDTOs.stream().map(WorkingHoursRequestDTO::dayOfWeek).toList();
        List<DayOfWeek> duplicateDays = requestedWorkingHours.stream()
                .filter(alreadyInsertedWorkingHours::contains)
                .toList();
        if (!duplicateDays.isEmpty())
            throw new WorkingHoursAlreadyPresentException(placeId, duplicateDays);


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

        return String.format(WorkingHoursResponseMessages.WORKING_HOURS_INSERT_MESSAGE, placeId);
    }

    /**
     * Editing {@link WorkingHours} data based on certain {@link DayOfWeek} and {@link Place}.
     *
     * @param placeId                          ID of the {@link Place}.
     * @param dayOfWeek                        Day provided by enum {@link DayOfWeek}.
     * @param workingHoursChangeTimeRequestDTO DTO with type {@link WorkingHoursChangeTimeRequestDTO} used for
     *                                         storing start and end time.
     * @return Response message of type {@link WorkingHoursResponseMessages}.
     */
    @Transactional
    @Override
    public String updateDailyWorkingHoursForPlace(UUID placeId, DayOfWeek dayOfWeek,
                                                  WorkingHoursChangeTimeRequestDTO workingHoursChangeTimeRequestDTO) {
        WorkingHours workingHours =
                workingHoursRepository.findByPlaceIdAndDayOfWeek(placeId, dayOfWeek)
                        .orElseThrow(() -> new WorkingHoursNotFoundException(placeId, dayOfWeek));

        LocalTime startTime = workingHoursChangeTimeRequestDTO.startTime();
        LocalTime endTime = workingHoursChangeTimeRequestDTO.endTime();
        workingHours.setStartTime(startTime);
        workingHours.setEndTime(endTime);

        workingHoursRepository.save(workingHours);

        return String.format(WorkingHoursResponseMessages.WORKING_HOURS_UPDATE_MESSAGE, dayOfWeek, placeId);
    }
}