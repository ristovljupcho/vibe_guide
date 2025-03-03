package com.vibe_guide.dtos;

import java.time.LocalDate;

public record EventPreviewResponseDTO (
        String eventName,
        String placeName,
        LocalDate startDate,
        LocalDate endDate

){
}
