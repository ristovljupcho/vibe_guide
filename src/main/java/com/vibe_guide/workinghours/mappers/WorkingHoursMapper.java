package com.vibe_guide.workinghours.mappers;

import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.workinghours.dtos.WorkingHoursMissingDaysResponseDTO;
import com.vibe_guide.workinghours.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.workinghours.entities.WorkingHours;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

/**
 *
 *
 * <ul>
 *   <li>This class is responsible for providing conversion methods from {@link WorkingHours} entity
 *       to Data Transfer Objects and vice versa.
 * </ul>
 */
@Component
public class WorkingHoursMapper {
  /**
   * Converts a {@link WorkingHours} entity to a {@link WorkingHoursResponseDTO} entity.
   *
   * @param workingHours {@link WorkingHours} entity to convert.
   * @return {@link WorkingHoursResponseDTO} converted entity.
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
  public WorkingHoursMissingDaysResponseDTO toWorkingHoursMissingDaysResponseDTO(
      DayOfWeek dayOfWeek) {

    return new WorkingHoursMissingDaysResponseDTO(dayOfWeek);
  }
}
