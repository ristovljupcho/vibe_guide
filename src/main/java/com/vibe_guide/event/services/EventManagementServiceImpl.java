package com.vibe_guide.event.services;

import com.vibe_guide.event.dtos.EventInsertRequestDTO;
import com.vibe_guide.event.dtos.EventUpdateRequestDTO;
import com.vibe_guide.event.entities.Event;
import com.vibe_guide.event.repositories.EventRepository;
import com.vibe_guide.event.utils.EventResponseMessages;
import com.vibe_guide.eventgallery.services.EventGalleryManagementService;
import com.vibe_guide.exceptions.EventNotFoundException;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class EventManagementServiceImpl implements EventManagementService {
  private final EventRepository eventRepository;
  private final PlaceRepository placeRepository;
  private final EventGalleryManagementService eventGalleryManagementService;

  @Override
  @Transactional
  public String insert(EventInsertRequestDTO eventInsertRequestDTO) {
    String name = eventInsertRequestDTO.name();
    String description = eventInsertRequestDTO.description();
    LocalDateTime startDate = eventInsertRequestDTO.startDate();
    LocalDateTime endDate = eventInsertRequestDTO.endDate();

    UUID placeId = eventInsertRequestDTO.placeId();
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    Event event = new Event();
    event.setName(name);
    event.setDescription(description);
    event.setStartDate(startDate);
    event.setEndDate(endDate);
    event.setPlace(place);
    eventRepository.save(event);

    List<MultipartFile> images = eventInsertRequestDTO.images();
    if (images != null && !images.isEmpty()) {
      eventGalleryManagementService.insertAll(event.getId(), images);
    }

    return EventResponseMessages.EVENT_INSERT_MESSAGE;
  }

  @Override
  @Transactional
  public String update(EventUpdateRequestDTO eventUpdateRequestDTO) {
    String name = eventUpdateRequestDTO.name();
    String description = eventUpdateRequestDTO.description();
    LocalDateTime startDate = eventUpdateRequestDTO.startDate();
    LocalDateTime endDate = eventUpdateRequestDTO.endDate();

    UUID placeId = eventUpdateRequestDTO.placeId();
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    UUID eventId = eventUpdateRequestDTO.eventId();
    Event event =
        eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

    event.setName(name);
    event.setDescription(description);
    event.setStartDate(startDate);
    event.setEndDate(endDate);
    event.setPlace(place);
    eventRepository.save(event);

    return EventResponseMessages.EVENT_UPDATE_MESSAGE;
  }

  @Override
  @Transactional
  public String delete(UUID eventId) {
    eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    eventGalleryManagementService.deleteAll(eventId);
    eventRepository.deleteById(eventId);

    return EventResponseMessages.EVENT_DELETE_MESSAGE;
  }
}
