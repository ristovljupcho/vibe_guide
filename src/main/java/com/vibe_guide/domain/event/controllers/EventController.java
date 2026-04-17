package com.vibe_guide.domain.event.controllers;

import com.vibe_guide.domain.event.dtos.EventInsertRequestDTO;
import com.vibe_guide.domain.event.dtos.EventResponseDTO;
import com.vibe_guide.domain.event.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.domain.event.dtos.EventUpdateRequestDTO;
import com.vibe_guide.domain.event.services.EventManagementService;
import com.vibe_guide.domain.event.services.EventQueryService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
public class EventController {

  private final EventQueryService eventQueryService;
  private final EventManagementService eventManagementService;

  @GetMapping("/paginated")
  ResponseEntity<Page<EventResponseDTO>> getPaginated(
      @RequestParam(required = false) String placeName,
      @RequestParam(required = false) LocalDateTime startDate,
      @RequestParam(required = false) LocalDateTime endDate,
      @RequestParam Integer page,
      @RequestParam Integer size) {
    EventSearchCriteriaDTO searchCriteria =
        new EventSearchCriteriaDTO(placeName, startDate, endDate);
    Page<EventResponseDTO> response = eventQueryService.getPaginated(searchCriteria, page, size);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/past/{placeId}")
  ResponseEntity<List<EventResponseDTO>> getPastByPlaceId(@PathVariable UUID placeId) {
    List<EventResponseDTO> response = eventQueryService.getPastByPlaceId(placeId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/upcoming")
  ResponseEntity<List<EventResponseDTO>> getUpcoming() {
    List<EventResponseDTO> response = eventQueryService.getUpcoming();

    return ResponseEntity.ok(response);
  }

  @GetMapping("/upcoming/{placeId}")
  ResponseEntity<List<EventResponseDTO>> getUpcomingByPlaceId(@PathVariable UUID placeId) {
    List<EventResponseDTO> response = eventQueryService.getUpcomingByPlaceId(placeId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/active")
  ResponseEntity<List<EventResponseDTO>> getActive() {
    List<EventResponseDTO> response = eventQueryService.getActive();

    return ResponseEntity.ok(response);
  }

  @GetMapping("/active/{placeId}")
  ResponseEntity<List<EventResponseDTO>> getActiveByPlaceId(@PathVariable UUID placeId) {
    List<EventResponseDTO> response = eventQueryService.getActiveByPlaceId(placeId);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/insert")
  ResponseEntity<String> insert(@ModelAttribute @Valid EventInsertRequestDTO dto) {
    String response = eventManagementService.insert(dto);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/update")
  ResponseEntity<String> update(@RequestBody @Valid EventUpdateRequestDTO dto) {
    String response = eventManagementService.update(dto);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{eventId}/delete")
  ResponseEntity<String> delete(@PathVariable UUID eventId) {
    String response = eventManagementService.delete(eventId);

    return ResponseEntity.ok(response);
  }
}

