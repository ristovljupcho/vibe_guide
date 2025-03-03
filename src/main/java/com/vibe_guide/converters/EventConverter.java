package com.vibe_guide.converters;

import com.vibe_guide.dtos.EventPreviewResponseDTO;
import com.vibe_guide.entities.Event;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * <ul>
 *     <li>Performs conversion of object from type {@link Event} to {@link EventPreviewResponseDTO}.</li>
 * </ul>
 */
@Component
public class EventConverter {

    /**
     * Converts a {@link Event} entity to a {@link EventPreviewResponseDTO} entity.
     * @param event {@link Event} entity to convert.
     * @return {@link EventPreviewResponseDTO} converted entity.
     */
    public EventPreviewResponseDTO toEventPreviewResponseDTO(Event event) {
        String eventName = event.getName();
        String placeName = event.getPlace().getName();
        LocalDate startDate = event.getStartDate().toLocalDate();
        LocalDate endDate = event.getEndDate().toLocalDate();

        return new EventPreviewResponseDTO(eventName, placeName, startDate, endDate);
    }
}