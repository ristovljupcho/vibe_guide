package com.vibe_guide.entities.composite_keys;

import com.vibe_guide.enums.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHoursId implements Serializable {
    private DayOfWeek dayOfWeek;
    private UUID place;
}
