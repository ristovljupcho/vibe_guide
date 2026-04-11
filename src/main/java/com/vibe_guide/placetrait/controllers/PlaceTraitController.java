package com.vibe_guide.placetrait.controllers;

import com.vibe_guide.placetrait.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.placetrait.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.placetrait.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.placetrait.services.PlaceTraitManagementService;
import com.vibe_guide.placetrait.services.PlaceTraitQueryService;
import com.vibe_guide.trait.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.trait.dtos.TraitResponseDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
@Validated
public class PlaceTraitController {

  private final PlaceTraitQueryService placeTraitQueryService;
  private final PlaceTraitManagementService placeTraitManagementService;

  @GetMapping("/{placeId}/traits/carousel")
  public ResponseEntity<List<TraitCarouselResponseDTO>> getTraitsForCarousel(
      @PathVariable UUID placeId) {
    List<TraitCarouselResponseDTO> response =
        placeTraitQueryService.getTraitsForDisplayInPlaceCarousel(placeId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{placeId}/top-traits")
  public ResponseEntity<List<TraitResponseDTO>> getTopTraitsForPlace(@PathVariable UUID placeId) {
    List<TraitResponseDTO> response = placeTraitQueryService.getTopTraitsForPlace(placeId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{placeId}/missing-traits")
  public ResponseEntity<List<TraitResponseDTO>> getMissingTraitsForPlace(
      @PathVariable UUID placeId) {
    List<TraitResponseDTO> response = placeTraitQueryService.getMissingTraitsForPlace(placeId);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/insert-trait")
  public ResponseEntity<String> insertTraitForPlace(
      @RequestBody @Validated PlaceTraitRequestDTO dto) {
    String response = placeTraitManagementService.insertSingleTraitInPlace(dto);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/batch-insert-traits")
  public ResponseEntity<String> batchInsertTraitForPlace(
      @RequestBody @Validated BatchInsertTraitsInPlace dto) {
    String response = placeTraitManagementService.batchInsertTraitsInPlace(dto);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/traits/update")
  public ResponseEntity<String> updateTraitForPlace(
      @RequestBody @Validated PlaceTraitRequestDTO dto) {
    String response = placeTraitManagementService.updateTraitForPlace(dto);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/traits/delete/{placeTraitId}")
  public ResponseEntity<String> deleteTraitForPlace(@PathVariable UUID placeTraitId) {
    String response = placeTraitManagementService.deleteSingleTraitInPlace(placeTraitId);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/batch-delete-traits")
  public ResponseEntity<String> deleteTraitForPlace(
      @RequestBody @Validated BatchDeleteTraitsInPlace dto) {
    String response = placeTraitManagementService.batchDeleteTraitsInPlace(dto);

    return ResponseEntity.ok(response);
  }
}
