package com.vibe_guide.controllers;


import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.services.EventManagementService;
import com.vibe_guide.services.EventQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
public class EventController {

    private final EventQueryService eventQueryService;
    private final EventManagementService eventManagementService;

    @GetMapping("/{placeId}/past")
    ResponseEntity<List<EventResponseDTO>> getPastEventsByPlaceId(@PathVariable UUID placeId) {
        List<EventResponseDTO> response = eventQueryService.findPastEventsByPlaceId(placeId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{placeId}/upcoming")
    ResponseEntity<List<EventResponseDTO>> getUpcomingEventsByPlaceId(@PathVariable UUID placeId) {
        List<EventResponseDTO> response = eventQueryService.findUpcomingEventsByPlaceId(placeId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/upcoming")
    ResponseEntity<List<EventResponseDTO>> getUpcomingEvents() {
        List<EventResponseDTO> response = eventQueryService.findUpcomingEvents();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{placeId}/todays")
    ResponseEntity<List<EventResponseDTO>> getTodaysEventsByPlaceId(@PathVariable UUID placeId) {
        List<EventResponseDTO> response = eventQueryService.findTodaysEventsByPlaceId(placeId);

        return ResponseEntity.ok(response);
    }
}
