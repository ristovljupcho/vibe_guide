package com.vibe_guide.services;

import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.dtos.EventSearchCriteriaDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface EventQueryService {
    Page<EventResponseDTO> findByCriteria(EventSearchCriteriaDTO searchCriteria, int page, int size);
    List<EventResponseDTO> findPastEvents(UUID placeId);
    List<EventResponseDTO> findEventsByPlaceId(UUID placeId);
}
