package com.vibe_guide.event.controllers;


import com.vibe_guide.event.dtos.EventInsertRequestDTO;
import com.vibe_guide.event.dtos.EventResponseDTO;
import com.vibe_guide.event.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.event.dtos.EventUpdateRequestDTO;
import com.vibe_guide.event.services.EventManagementService;
import com.vibe_guide.event.services.EventQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
public class EventController {

    private final EventQueryService eventQueryService;
    private final EventManagementService eventManagementService;

    @GetMapping("/paginated")
    ResponseEntity<Page<EventResponseDTO>> getPaginatedEvents(
            @RequestParam(required = false) String placeName,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        EventSearchCriteriaDTO searchCriteria =
                new EventSearchCriteriaDTO(placeName, startDate, endDate);
        Page<EventResponseDTO> response = eventQueryService.getPaginatedEvents(searchCriteria, page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/past/{placeId}")
    ResponseEntity<List<EventResponseDTO>> getPastEventsByPlaceId(@PathVariable UUID placeId) {
        List<EventResponseDTO> response = eventQueryService.findPastEventsByPlaceId(placeId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/upcoming")
    ResponseEntity<List<EventResponseDTO>> getUpcomingEvents() {
        List<EventResponseDTO> response = eventQueryService.findUpcomingEvents();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/upcoming/{placeId}")
    ResponseEntity<List<EventResponseDTO>> getUpcomingEventsByPlaceId(@PathVariable UUID placeId) {
        List<EventResponseDTO> response = eventQueryService.findUpcomingEventsByPlaceId(placeId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    ResponseEntity<List<EventResponseDTO>> getActiveEvents() {
        List<EventResponseDTO> response = eventQueryService.findActiveEvents();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active/{placeId}")
    ResponseEntity<List<EventResponseDTO>> getActiveEventsByPlaceId(@PathVariable UUID placeId) {
        List<EventResponseDTO> response = eventQueryService.findActiveEventsByPlaceId(placeId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert")
    ResponseEntity<String> insertEvent(@RequestBody @Valid EventInsertRequestDTO dto) {
        String response = eventManagementService.insertEvent(dto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    ResponseEntity<String> updateEvent(@RequestBody @Valid EventUpdateRequestDTO dto) {
        String response = eventManagementService.updateEvent(dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{eventId}/delete")
    ResponseEntity<String> deleteEvent(@PathVariable UUID eventId) {
        String response = eventManagementService.deleteEvent(eventId);

        return ResponseEntity.ok(response);
    }
}
