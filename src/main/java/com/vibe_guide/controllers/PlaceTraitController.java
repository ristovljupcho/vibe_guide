package com.vibe_guide.controllers;

import com.vibe_guide.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.services.PlaceTraitManagementService;
import com.vibe_guide.services.PlaceTraitQueryService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places/{placeId}")
@Validated
public class PlaceTraitController {

    private final PlaceTraitQueryService placeTraitQueryService;
    private final PlaceTraitManagementService placeTraitManagementService;

    @GetMapping("/traits/carousel")
    public ResponseEntity<List<TraitCarouselResponseDTO>> getTraitsForCarousel(@PathVariable UUID placeId) {
        List<TraitCarouselResponseDTO> response = placeTraitQueryService.getTraitsForDisplayInPlaceCarousel(placeId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-traits")
    public ResponseEntity<List<TraitResponseDTO>> getTopTraitsForPlace(@PathVariable UUID placeId) {
        List<TraitResponseDTO> response = placeTraitQueryService.getTopTraitsForPlace(placeId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/missing-traits")
    public ResponseEntity<List<TraitResponseDTO>> getMissingTraitsForPlace(@PathVariable UUID placeId) {
        List<TraitResponseDTO> response = placeTraitQueryService.getMissingTraitsForPlace(placeId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert-trait")
    public ResponseEntity<String> insertTraitForPlace(@PathVariable UUID placeId,
                                                      @RequestBody @Validated PlaceTraitRequestDTO dto) {
        String response = placeTraitManagementService.insertSingleTraitInPlace(placeId, dto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch-insert-traits")
    public ResponseEntity<String> batchInsertTraitForPlace(@PathVariable UUID placeId,
                                                           @RequestBody @Validated BatchInsertTraitsInPlace dto) {
        String response = placeTraitManagementService.batchInsertTraitsInPlace(placeId, dto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/traits/update")
    public ResponseEntity<String> updateTraitForPlace(@PathVariable UUID placeId,
                                                     @RequestBody @Validated PlaceTraitRequestDTO dto) {
        String response = placeTraitManagementService.updateTraitForPlace(placeId, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/traits/delete/{traitId}")
    public ResponseEntity<String> deleteTraitForPlace(@PathVariable UUID placeId, @PathVariable UUID traitId) {
        String response = placeTraitManagementService.deleteSingleTraitInPlace(placeId, traitId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/batch-delete-traits")
    public ResponseEntity<String> deleteTraitForPlace(@PathVariable UUID placeId,
                                                      @RequestBody @Validated BatchDeleteTraitsInPlace dto) {
        String response = placeTraitManagementService.batchDeleteTraitsInPlace(placeId, dto);

        return ResponseEntity.ok(response);
    }
}