package com.vibe_guide.converters;

import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.dtos.WorkingHoursMissingDaysResponseDTO;
import com.vibe_guide.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.entities.WorkingHours;
import com.vibe_guide.enums.DayOfWeek;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * <ul>
 *     <li>This class is responsible for providing conversion methods from {@link WorkingHours} entity to Data Transfer
 *     Objects and vice versa.</li>
 * </ul>
 */
@Component
public class WorkingHoursConverter {
    /**
     * Converts a {@link WorkingHours} entity to a {@link WorkingHoursResponseDTO} entity.
     *
     * @param workingHours {@link WorkingHours} entity to convert.
     * @return {@link DailyOfferResponseDTO} converted entity.
     */
    public WorkingHoursResponseDTO toWorkingHoursResponseDTO(WorkingHours workingHours) {
        DayOfWeek dayOfWeek = workingHours.getDayOfWeek();
        LocalTime startTime = workingHours.getStartTime();
        LocalTime endTime = workingHours.getEndTime();

        return new WorkingHoursResponseDTO(dayOfWeek, startTime, endTime);
    }

    /**
     * Converts a {@link DayOfWeek} entity to a {@link WorkingHoursMissingDaysResponseDTO} entity.
     *
     * @param dayOfWeek {@link DayOfWeek} entity to convert.
     * @return {@link WorkingHoursMissingDaysResponseDTO} converted entity.
     */
    public WorkingHoursMissingDaysResponseDTO toWorkingHoursMissingDaysResponseDTO(DayOfWeek dayOfWeek) {

        return new WorkingHoursMissingDaysResponseDTO(dayOfWeek);
    }
}