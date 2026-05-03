package com.vibe_guide.domain.event.services.impl;

import com.vibe_guide.domain.event.services.EventQueryService;
import com.vibe_guide.domain.event.dtos.EventResponseDTO;
import com.vibe_guide.domain.event.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.domain.event.entities.Event;
import com.vibe_guide.domain.event.mappers.EventMapper;
import com.vibe_guide.domain.event.repositories.EventRepository;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.specifications.EventSpecification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventQueryServiceImpl implements EventQueryService {
  private final EventRepository eventRepository;
  private final PlaceRepository placeRepository;
  private final EventMapper eventMapper;

  @Override
  public Page<EventResponseDTO> getPaginated(
      EventSearchCriteriaDTO searchCriteria, int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Specification<Event> spec = Specification.where(null);

    LocalDateTime startDate = searchCriteria.startDate();
    if (startDate != null) {
      spec = spec.and(EventSpecification.startsOnOrAfter(startDate));
    }

    LocalDateTime endDate = searchCriteria.endDate();
    if (endDate != null) {
      spec = spec.and(EventSpecification.endsOnOrBefore(endDate));
    }

    Page<Event> pageOfEvents = eventRepository.findAll(spec, pageRequest);

    return pageOfEvents.map(eventMapper::toEventResponseDTO);
  }

  @Override
  public List<EventResponseDTO> getPastByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

    List<Event> pastEvents = eventRepository.findPastEvents(placeId, now, oneMonthAgo);
    return pastEvents.stream().map(eventMapper::toEventResponseDTO).toList();
  }

  @Override
  public List<EventResponseDTO> getUpcoming() {
    LocalDateTime now = LocalDateTime.now();
    List<Event> upcomingEvents = eventRepository.getUpcoming(now);

    return upcomingEvents.stream().map(eventMapper::toEventResponseDTO).toList();
  }

  @Override
  public List<EventResponseDTO> getUpcomingByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);
    List<Event> upcomingEventsByPlaceId = eventRepository.getUpcomingByPlaceId(placeId);

    return upcomingEventsByPlaceId.stream().map(eventMapper::toEventResponseDTO).toList();
  }

  @Override
  public List<EventResponseDTO> getActive() {
    LocalDateTime now = LocalDateTime.now();
    List<Event> activeEvents = eventRepository.getActive(now);

    return activeEvents.stream().map(eventMapper::toEventResponseDTO).toList();
  }

  @Override
  public List<EventResponseDTO> getActiveByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);
    LocalDateTime now = LocalDateTime.now();
    List<Event> activeEventsByPlaceId = eventRepository.getActiveByPlaceId(placeId, now);

    return activeEventsByPlaceId.stream().map(eventMapper::toEventResponseDTO).toList();
  }

  private void checkIfPlaceExists(UUID placeId) {
    placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
  }
}


