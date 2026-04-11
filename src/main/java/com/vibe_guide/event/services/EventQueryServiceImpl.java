package com.vibe_guide.event.services;

import com.vibe_guide.event.mappers.EventMapper;
import com.vibe_guide.event.dtos.EventResponseDTO;
import com.vibe_guide.event.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.event.entities.Event;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.event.repositories.EventRepository;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.event.services.EventQueryService;
import com.vibe_guide.specifications.EventSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventQueryServiceImpl implements EventQueryService {
    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;
    private final EventMapper eventMapper;

    /**
     * Retrieves {@link Event} objects using pagination. Filtering is enabled using {@link EventSpecification} which
     * will display {@link Event} using dynamic queries.
     *
     * @param searchCriteria dto used for the attributes in {@link EventSpecification}
     * @param page           page number
     * @param size           page size
     * @return A {@link Page} containing {@link EventResponseDTO} objects.
     */
    @Override
    public Page<EventResponseDTO> getPaginatedEvents(EventSearchCriteriaDTO searchCriteria, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Specification<Event> spec = Specification.where(null);

        String eventName = searchCriteria.eventName();
        if (eventName != null && !eventName.isEmpty()) {
            spec = spec.and(EventSpecification.containsEventName(eventName));
        }

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

    /**
     * Retrieves a list of past {@link Event} objects that took place in the last month at a certain place.
     *
     * @param placeId uuid of the Place used for filtering
     * @return A list of {@link EventResponseDTO} containing event details.
     */
    @Override
    public List<EventResponseDTO> findPastEventsByPlaceId(UUID placeId) {
        checkIfPlaceExists(placeId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        List<Event> pastEvents = eventRepository.findPastEvents(placeId, now, oneMonthAgo);
        return pastEvents.stream().map(eventMapper::toEventResponseDTO).toList();
    }

    /**
     * Retrieves a list of upcoming {@link Event} objects
     *
     * @return A list of {@link EventResponseDTO} containing event details.
     */
    @Override
    public List<EventResponseDTO> findUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> upcomingEvents = eventRepository.findUpcomingEvents(now);

        return upcomingEvents.stream().map(eventMapper::toEventResponseDTO).toList();
    }

    /**
     * Retrieves a list of upcoming {@link Event} objects that are happening in that Place
     *
     * @param placeId uuid of the Place used for filtering
     * @return A list of {@link EventResponseDTO} containing event details.
     */
    @Override
    public List<EventResponseDTO> findUpcomingEventsByPlaceId(UUID placeId) {
        checkIfPlaceExists(placeId);
        List<Event> upcomingEventsByPlaceId = eventRepository.findUpcomingEventsByPlaceId(placeId);

        return upcomingEventsByPlaceId.stream().map(eventMapper::toEventResponseDTO).toList();
    }

    @Override
    public List<EventResponseDTO> findActiveEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> activeEvents = eventRepository.findActiveEvents(now);

        return activeEvents.stream().map(eventMapper::toEventResponseDTO).toList();
    }

    /**
     * Retrieves a list of today {@link Event} objects that are happening in that Place
     *
     * @param placeId uuid of the Place used for filtering
     * @return A list of {@link EventResponseDTO} containing event details.
     */
    @Override
    public List<EventResponseDTO> findActiveEventsByPlaceId(UUID placeId) {
        checkIfPlaceExists(placeId);
        LocalDateTime now = LocalDateTime.now();
        List<Event> activeEventsByPlaceId = eventRepository.findActiveEventsByPlaceId(placeId, now);

        return activeEventsByPlaceId.stream().map(eventMapper::toEventResponseDTO).toList();
    }

    private void checkIfPlaceExists(UUID placeId) {
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    }
}
