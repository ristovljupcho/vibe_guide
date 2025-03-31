package com.vibe_guide.services.impl;

import com.vibe_guide.converters.WorkingHoursConverter;
import com.vibe_guide.dtos.WorkingHoursMissingDaysResponseDTO;
import com.vibe_guide.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.WorkingHours;
import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.WorkingHoursRepository;
import com.vibe_guide.services.WorkingHoursQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class WorkingHoursQueryServiceImpl implements WorkingHoursQueryService {

    WorkingHoursRepository workingHoursRepository;
    PlaceRepository placeRepository;
    WorkingHoursConverter workingHoursConverter;

    /**
     * Retrieves all 7 tuples/weekly {@link WorkingHours} for a {@link Place} with provided ID.
     *
     * @param placeId ID of the targeted {@link Place}.
     * @return List of {@link WorkingHoursResponseDTO}.
     */
    @Override
    public List<WorkingHoursResponseDTO> getWeeklyWorkingHoursForPlace(UUID placeId) {
        checkIfPlaceExists(placeId);

        List<WorkingHours> weeklyWorkingHours = workingHoursRepository.findAllByPlaceId(placeId);

        return weeklyWorkingHours.stream().map(workingHoursConverter::toWorkingHoursResponseDTO).toList();
    }

    /**
     * Retrieves all {@link DayOfWeek} days for which {@link Place} doesn't have {@link WorkingHours} defined.
     *
     * @param placeId ID of the targeted {@link Place}.
     * @return List of {@link WorkingHoursMissingDaysResponseDTO} for missing days.
     */
    @Override
    public List<WorkingHoursMissingDaysResponseDTO> getMissingWorkingHoursDaysForPlace(UUID placeId) {
        checkIfPlaceExists(placeId);

        Set<DayOfWeek> existingDays = workingHoursRepository.findAllByPlaceId(placeId)
                .stream()
                .map(WorkingHours::getDayOfWeek)
                .collect(Collectors.toSet());
        Stream<DayOfWeek> missingDays = Arrays.stream(DayOfWeek.values()).filter(day -> !existingDays.contains(day));

        return missingDays.map(workingHoursConverter::toWorkingHoursMissingDaysResponseDTO).toList();
    }

    private void checkIfPlaceExists(UUID placeId) {
        if (!placeRepository.existsById(placeId))
            throw new PlaceNotFoundException(placeId);
    }
}