package com.vibe_guide.visitedplace.controllers;

import com.vibe_guide.visitedplace.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.visitedplace.services.VisitedPlaceService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visited-places")
@Validated
public class VisitedPlaceController {
  private final VisitedPlaceService visitedPlaceService;

  @GetMapping("/{userId}")
  public ResponseEntity<List<VisitedPlaceResponseDTO>> getVisitedPlacesByUserId(
      @PathVariable UUID userId) {
    List<VisitedPlaceResponseDTO> response = visitedPlaceService.getVisitedPlacesByUserId(userId);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/{userId}/{placeId}")
  public ResponseEntity<String> manageVisitedPlace(
      @PathVariable UUID userId, @PathVariable UUID placeId) {
    String result = visitedPlaceService.manageVisitedPlace(userId, placeId);
    return ResponseEntity.ok(result);
  }
}
