package com.vibe_guide.domain.workinghours.entities;

import com.vibe_guide.enums.DayOfWeek;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursId implements Serializable {
  private DayOfWeek dayOfWeek;
  private UUID place;
}

