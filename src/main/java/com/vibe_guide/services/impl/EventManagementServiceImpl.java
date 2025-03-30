package com.vibe_guide.services.impl;


import com.vibe_guide.dtos.EventInsertRequestDTO;
import com.vibe_guide.dtos.EventUpdateRequestDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.Place;
import com.vibe_guide.exceptions.EventNotFoundException;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.EventGalleryManagementService;
import com.vibe_guide.services.EventManagementService;
import com.vibe_guide.utils.EventResponseMessages;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventManagementServiceImpl implements EventManagementService {
    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;
    private final EventGalleryManagementService eventGalleryManagementService;

    /**
     * Inserts a new {@link Event}  with provided {@link EventInsertRequestDTO}.
     *
     * @param eventInsertRequestDTO DTO used to insert new {@link Event} by providing:
     *                              String name, String description,LocalDateTime startDate,
     *                              LocalDateTime endDate and UUID placeId
     * @return Response message of type {@link EventResponseMessages}
     */
    @Override
    @Transactional
    public String insertEvent(EventInsertRequestDTO eventInsertRequestDTO) {
        String name = eventInsertRequestDTO.name();
        String description = eventInsertRequestDTO.description();
        LocalDateTime startDate = eventInsertRequestDTO.startDate();
        LocalDateTime endDate = eventInsertRequestDTO.endDate();

        UUID placeId = eventInsertRequestDTO.placeId();
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setPlace(place);
        eventRepository.save(event);

        List<MultipartFile> images = eventInsertRequestDTO.images();
        if (images != null && !images.isEmpty()) {
            eventGalleryManagementService.addImagesToEvent(event.getId(), images);
        }

        return EventResponseMessages.EVENT_INSERT_MESSAGE;
    }

    /**
     * Updates a {@link Event}      object with provided {@link EventUpdateRequestDTO}
     *
     * @param eventUpdateRequestDTO DTO used to update {@link Event} object by providing UUID eventId,
     *                              String name, String description,LocalDateTime startDate,
     *                              LocalDateTime endDate and UUID placeId
     * @return Response message of type {@link EventResponseMessages}
     */
    @Override
    @Transactional
    public String updateEvent(EventUpdateRequestDTO eventUpdateRequestDTO) {
        String name = eventUpdateRequestDTO.name();
        String description = eventUpdateRequestDTO.description();
        LocalDateTime startDate = eventUpdateRequestDTO.startDate();
        LocalDateTime endDate = eventUpdateRequestDTO.endDate();

        UUID placeId = eventUpdateRequestDTO.placeId();
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

        UUID eventId = eventUpdateRequestDTO.eventId();
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        event.setName(name);
        event.setDescription(description);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setPlace(place);
        eventRepository.save(event);

        return EventResponseMessages.EVENT_UPDATE_MESSAGE;
    }

    /**
     * Deletes a {@link Event} object with provided <b><i>UUID eventId</i></b>.
     *
     * @param eventId UUID of the {@link Event} object that needs to be deleted.
     * @return Response message of type {@link EventResponseMessages}
     */
    @Override
    @Transactional
    public String deleteEvent(UUID eventId) {
        Optional<Event> event = Optional.ofNullable(
                eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId)));
        if (event.isEmpty())
            throw new EventNotFoundException(eventId);

        eventRepository.deleteById(eventId);

        return EventResponseMessages.EVENT_DELETE_MESSAGE;
    }
}