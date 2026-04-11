package com.vibe_guide.place.controllers;

import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.place.dtos.PlaceRequestDTO;
import com.vibe_guide.place.dtos.PlaceResponseDTO;
import com.vibe_guide.place.services.PlaceManagementService;
import com.vibe_guide.place.services.PlaceQueryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
@Validated
public class PlaceController {

  private final PlaceQueryService placeQueryService;
  private final PlaceManagementService placeManagementService;

  @GetMapping
  ResponseEntity<List<PlacePreviewResponseDTO>> getAll(
      @RequestParam(required = false) List<String> traits,
      @RequestParam(required = false) PlaceSortBy sortBy,
      @RequestParam(required = false) SortDirection sortDirection) {
    List<PlacePreviewResponseDTO> response =
        placeQueryService.getAll(traits, sortBy, sortDirection);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/top")
  ResponseEntity<List<PlacePreviewResponseDTO>> getTop() {
    List<PlacePreviewResponseDTO> response = placeQueryService.getTop();

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{placeId}")
  ResponseEntity<PlaceResponseDTO> getById(@PathVariable UUID placeId) {
    PlaceResponseDTO response = placeQueryService.getById(placeId);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/update")
  ResponseEntity<String> update(@RequestBody @Valid PlaceRequestDTO dto) {
    String response = placeManagementService.update(dto);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete/{placeId}")
  ResponseEntity<String> delete(@PathVariable UUID placeId) {
    String response = placeManagementService.delete(placeId);

    return ResponseEntity.ok(response);
  }
}
