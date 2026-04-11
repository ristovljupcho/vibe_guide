package com.vibe_guide.event.services;

import com.vibe_guide.event.dtos.EventResponseDTO;
import com.vibe_guide.event.dtos.EventSearchCriteriaDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface EventQueryService {
  Page<EventResponseDTO> getPaginated(EventSearchCriteriaDTO searchCriteria, int page, int size);

  List<EventResponseDTO> getPastByPlaceId(UUID placeId);

  List<EventResponseDTO> getUpcoming();

  List<EventResponseDTO> getUpcomingByPlaceId(UUID placeId);

  List<EventResponseDTO> getActive();

  List<EventResponseDTO> getActiveByPlaceId(UUID placeId);
}
