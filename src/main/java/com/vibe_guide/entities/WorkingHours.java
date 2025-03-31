package com.vibe_guide.entities;

import com.vibe_guide.entities.composite_keys.WorkingHoursId;
import com.vibe_guide.enums.DayOfWeek;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@IdClass(WorkingHoursId.class)
public class WorkingHours {
    @Id
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @ToString.Exclude
    private Place place;

    private LocalTime startTime;

    private LocalTime endTime;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WorkingHours that = (WorkingHours) o;
        return Objects.equals(dayOfWeek, that.dayOfWeek) &&
                Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, place);
    }
}
