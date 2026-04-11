package com.vibe_guide.workinghours.mappers;

import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.workinghours.dtos.WorkingHoursMissingDaysResponseDTO;
import com.vibe_guide.workinghours.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.workinghours.entities.WorkingHours;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class WorkingHoursMapper {
  public WorkingHoursResponseDTO toWorkingHoursResponseDTO(WorkingHours workingHours) {
    DayOfWeek dayOfWeek = workingHours.getDayOfWeek();
    LocalTime startTime = workingHours.getStartTime();
    LocalTime endTime = workingHours.getEndTime();

    return new WorkingHoursResponseDTO(dayOfWeek, startTime, endTime);
  }

  public WorkingHoursMissingDaysResponseDTO toWorkingHoursMissingDaysResponseDTO(
      DayOfWeek dayOfWeek) {

    return new WorkingHoursMissingDaysResponseDTO(dayOfWeek);
  }
}
