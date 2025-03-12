package com.vibe_guide.services.impl;

import com.vibe_guide.converters.EventConverter;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.repositories.EventRepository;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventQueryServiceImpl implements EventQueryService {
    private final EventRepository eventRepository;
    private final EventConverter eventConverter;


    /**
     * Retrieves {@link Event} objects using pagination. Filtering is enabled using {@link EventSpecification} which
     * will display {@link Event} using dynamic queries.
     *
     * @param searchCriteria    dto used for the attributes in {@link EventSpecification}
     * @param page              page number
     * @param size              page size
     * @return A {@link Page} containing {@link EventResponseDTO} objects.
     */
    @Override
    public Page<EventResponseDTO> findByCriteria(EventSearchCriteriaDTO searchCriteria, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Specification<Event> spec = Specification.where(null);

        UUID placeId = searchCriteria.placeId();
        if(placeId != null) {
            spec = spec.and(EventSpecification.hasPlaceId(placeId));
        }

        String eventName = searchCriteria.eventName();
        if(eventName != null && !eventName.isEmpty()) {
            spec = spec.and(EventSpecification.containsEventName(eventName));
        }
        LocalDateTime startDate = searchCriteria.startDate();
        if(startDate != null) {
            spec = spec.and(EventSpecification.startsOnOrAfter(startDate));
        }
        LocalDateTime endDate = searchCriteria.endDate();
        if(endDate != null) {
            spec = spec.and(EventSpecification.endsOnOrBefore(endDate));
        }

        return eventRepository.findAll(spec, pageRequest).map(eventConverter::toEventResponseDTO);
    }

    /**
     * Retrieves a list of past {@link Event} objects that took place in the last month at a certain place.
     *
     * @param placeId   uuid of the Place used for filtering
     * @return A list of {@link EventResponseDTO} containing event details.
     */
    @Override public List<EventResponseDTO> findPastEvents(UUID placeId) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        LocalDateTime now = LocalDateTime.now();

        List<Event> pastEvents = eventRepository.findPastEvents(placeId, oneMonthAgo, now);
        return pastEvents.stream().map(eventConverter::toEventResponseDTO).collect(Collectors.toList());
    }
}