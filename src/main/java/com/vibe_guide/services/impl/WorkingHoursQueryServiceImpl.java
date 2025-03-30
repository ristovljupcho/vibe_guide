package com.vibe_guide.services.impl;

import com.vibe_guide.converters.WorkingHoursConverter;
import com.vibe_guide.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.WorkingHours;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.WorkingHoursRepository;
import com.vibe_guide.services.WorkingHoursQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        if (!placeRepository.existsById(placeId))
            throw new PlaceNotFoundException(placeId);

        List<WorkingHours> weeklyWorkingHours = workingHoursRepository.findAllByPlaceId(placeId);

        return weeklyWorkingHours.stream().map(workingHoursConverter::toWorkingHoursResponseDTO).toList();
    }
}