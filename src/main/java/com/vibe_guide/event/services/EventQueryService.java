package com.vibe_guide.event.services;

import com.vibe_guide.event.dtos.EventResponseDTO;
import com.vibe_guide.event.dtos.EventSearchCriteriaDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface EventQueryService {
  Page<EventResponseDTO> getPaginatedEvents(
      EventSearchCriteriaDTO searchCriteria, int page, int size);

  List<EventResponseDTO> findPastEventsByPlaceId(UUID placeId);

  List<EventResponseDTO> findUpcomingEvents();

  List<EventResponseDTO> findUpcomingEventsByPlaceId(UUID placeId);

  List<EventResponseDTO> findActiveEvents();

  List<EventResponseDTO> findActiveEventsByPlaceId(UUID placeId);
}
