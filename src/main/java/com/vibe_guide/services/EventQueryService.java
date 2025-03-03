package com.vibe_guide.services;

import com.vibe_guide.dtos.EventPreviewResponseDTO;
import com.vibe_guide.dtos.EventSearchCriteriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventQueryService {
    Page<EventPreviewResponseDTO> findAllEvents(Pageable pageable);
    Page<EventPreviewResponseDTO> findByCriteria(EventSearchCriteriaDTO searchCriteria, Pageable pageable);
}
