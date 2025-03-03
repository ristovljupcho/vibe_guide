package com.vibe_guide.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public class EventSearchCriteriaDTO {
    UUID placeId;
    String placeName;
    String eventName;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Boolean isToday;
}
