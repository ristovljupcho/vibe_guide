package com.vibe_guide.domain.event.dtos;

import java.time.LocalDateTime;

public record EventSearchCriteriaDTO(
    // can be empty
    LocalDateTime startDate,
    // can be empty
    LocalDateTime endDate) {}
