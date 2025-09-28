package com.vibe_guide.dtos;

import java.time.LocalDateTime;

public record EventSearchCriteriaDTO(
        // can be empty
        String eventName,
        // can be empty
        LocalDateTime startDate,
        // can be empty
        LocalDateTime endDate
) {
}