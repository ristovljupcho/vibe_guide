package com.vibe_guide.converters;

import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.Place;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

/**
 * <ul>
 *     <li>This class is responsible for providing conversion methods from {@link Event} entity to Data Transfer
 *     Objects and vice versa.</li>
 * </ul>
 */
@Component
public class EventConverter {

    /**
     * Converts a {@link Event} entity to a {@link EventResponseDTO} entity.
     *
     * @param event {@link Event} entity to convert.
     * @return {@link EventResponseDTO} converted entity.
     */
    public EventResponseDTO toEventResponseDTO(Event event) {
        String eventName = event.getName();
        Place place = event.getPlace();
        String placeName = place.getName();
        String description = event.getDescription();
        LocalDateTime startDate = event.getStartDate();
        LocalDateTime endDate = event.getEndDate();

        List<String> base64Images = event.getGalleries().stream()
                .map(g -> Base64.getEncoder().encodeToString(g.getPhoto()))
                .toList();
        return new EventResponseDTO(eventName, placeName, description, startDate, endDate, base64Images);
    }
}