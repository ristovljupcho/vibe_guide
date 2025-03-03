package com.vibe_guide.services.impl;

import com.vibe_guide.converters.EventConverter;
import com.vibe_guide.dtos.EventPreviewResponseDTO;
import com.vibe_guide.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.services.EventQueryService;
import com.vibe_guide.specifications.EventSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EventQueryServiceImpl implements EventQueryService {
    private final EventRepository eventRepository;
    private final EventConverter eventConverter;

    @Override public Page<EventPreviewResponseDTO> findAllEvents(Pageable pageable) {
        return this.eventRepository.findAll(pageable).map(eventConverter::toEventPreviewResponseDTO);
    }

    @Override
    public Page<EventPreviewResponseDTO> findByCriteria(EventSearchCriteriaDTO searchCriteria, Pageable pageable) {
        Specification<Event> spec = Specification.where(null);

        if(searchCriteria.getPlaceId() != null) {
            spec = spec.and(EventSpecification.hasPlaceId(searchCriteria.getPlaceId()));
        }
        if(searchCriteria.getPlaceName() != null && !searchCriteria.getPlaceName().isEmpty()) {
            spec = spec.and(EventSpecification.hasPlaceName(searchCriteria.getPlaceName()));
        }
        if(searchCriteria.getEventName() != null && !searchCriteria.getEventName().isEmpty()) {
            spec = spec.and(EventSpecification.containsEventName(searchCriteria.getEventName()));
        }
        if(searchCriteria.getStartDate() != null && searchCriteria.getEndDate() != null) {
            spec = spec.and(EventSpecification.startDateBetween(searchCriteria.getStartDate(), searchCriteria.getEndDate()));
        }
        if (searchCriteria.getIsToday() != null && searchCriteria.getIsToday()) {
            spec = spec.and(EventSpecification.isToday());
        }

        return this.eventRepository.findAll(spec, pageable).map(eventConverter::toEventPreviewResponseDTO);
    }
}
