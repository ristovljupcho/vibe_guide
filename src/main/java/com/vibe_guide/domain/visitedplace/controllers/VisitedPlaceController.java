package com.vibe_guide.domain.visitedplace.controllers;

import com.vibe_guide.domain.visitedplace.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.domain.visitedplace.dtos.VisitedPlaceToggleRequestDTO;
import com.vibe_guide.domain.visitedplace.services.VisitedPlaceService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<List<VisitedPlaceResponseDTO>> getAllByUserId(@PathVariable String userId) {
    List<VisitedPlaceResponseDTO> response = visitedPlaceService.getAllByUserId(userId);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/toggle")
  public ResponseEntity<String> toggle(@RequestBody @Valid VisitedPlaceToggleRequestDTO dto) {
    String result = visitedPlaceService.toggle(dto);
    return ResponseEntity.ok(result);
  }
}

