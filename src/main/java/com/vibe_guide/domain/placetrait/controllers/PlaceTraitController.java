package com.vibe_guide.domain.placetrait.controllers;

import com.vibe_guide.domain.placetrait.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.domain.placetrait.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.domain.placetrait.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.domain.placetrait.services.PlaceTraitManagementService;
import com.vibe_guide.domain.placetrait.services.PlaceTraitQueryService;
import com.vibe_guide.domain.trait.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.domain.trait.dtos.TraitResponseDTO;
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
  public ResponseEntity<List<TraitCarouselResponseDTO>> getAllForCarouselByPlaceId(
      @PathVariable UUID placeId) {
    List<TraitCarouselResponseDTO> response =
        placeTraitQueryService.getAllForCarouselByPlaceId(placeId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{placeId}/top-traits")
  public ResponseEntity<List<TraitResponseDTO>> getTopByPlaceId(@PathVariable UUID placeId) {
    List<TraitResponseDTO> response = placeTraitQueryService.getTopByPlaceId(placeId);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{placeId}/missing-traits")
  public ResponseEntity<List<TraitResponseDTO>> getMissingByPlaceId(@PathVariable UUID placeId) {
    List<TraitResponseDTO> response = placeTraitQueryService.getMissingByPlaceId(placeId);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/insert-trait")
  public ResponseEntity<String> insert(@RequestBody @Validated PlaceTraitRequestDTO dto) {
    String response = placeTraitManagementService.insert(dto);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/batch-insert-traits")
  public ResponseEntity<String> insertAll(@RequestBody @Validated BatchInsertTraitsInPlace dto) {
    String response = placeTraitManagementService.insertAll(dto);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/traits/update")
  public ResponseEntity<String> update(@RequestBody @Validated PlaceTraitRequestDTO dto) {
    String response = placeTraitManagementService.update(dto);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/traits/delete/{placeTraitId}")
  public ResponseEntity<String> delete(@PathVariable UUID placeTraitId) {
    String response = placeTraitManagementService.delete(placeTraitId);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/batch-delete-traits")
  public ResponseEntity<String> deleteAll(@RequestBody @Validated BatchDeleteTraitsInPlace dto) {
    String response = placeTraitManagementService.deleteAll(dto);

    return ResponseEntity.ok(response);
  }
}

