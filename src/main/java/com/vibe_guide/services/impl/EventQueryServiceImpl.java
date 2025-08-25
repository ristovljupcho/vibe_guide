package com.vibe_guide.services.impl;

import com.vibe_guide.converters.EventConverter;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.EventQueryService;
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
    private final EventConverter eventConverter;

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

        UUID placeId = searchCriteria.placeId();
        if (placeId != null) {
            spec = spec.and(EventSpecification.hasPlaceId(placeId));
        }

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

        return pageOfEvents.map(eventConverter::toEventResponseDTO);
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
        return pastEvents.stream().map(eventConverter::toEventResponseDTO).toList();
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
        List<Event> events = eventRepository.findEventsByPlaceId(placeId);

        return events.stream().map(eventConverter::toEventResponseDTO).toList();
    }

    /**
     * Retrieves a list of today {@link Event} objects that are happening in that Place
     *
     * @param placeId uuid of the Place used for filtering
     * @return A list of {@link EventResponseDTO} containing event details.
     */
    @Override
    public List<EventResponseDTO> findTodaysEventsByPlaceId(UUID placeId) {
        checkIfPlaceExists(placeId);
        LocalDateTime today = LocalDateTime.now();
        List<Event> todayEvents = eventRepository.findTodayEventsByPlaceId(placeId, today);

        return todayEvents.stream().map(eventConverter::toEventResponseDTO).toList();
    }

    /**
     * Retrieves a list of upcoming {@link Event} objects
     *
     * @return A list of {@link EventResponseDTO} containing event details.
     */
    @Override
    public List<EventResponseDTO> findUpcomingEvents() {
        LocalDateTime today = LocalDateTime.now();
        List<Event> upcomingEvents = eventRepository.findUpcomingEvents(today);

        return upcomingEvents.stream().map(eventConverter::toEventResponseDTO).toList();
    }

    private void checkIfPlaceExists(UUID placeId) {
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    }
}