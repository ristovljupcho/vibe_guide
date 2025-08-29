package com.vibe_guide.controllers;

import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceRequestDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.services.PlaceManagementService;
import com.vibe_guide.services.PlaceQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
@Validated
public class PlaceController {

    private final PlaceQueryService placeQueryService;
    private final PlaceManagementService placeManagementService;

    @GetMapping
    ResponseEntity<Page<PlacePreviewResponseDTO>> getPaginatedPlaces(
            @RequestParam(required = false) List<String> traits,
            @RequestParam(required = false) PlaceSortBy sortBy,
            @RequestParam(required = false) SortDirection sortDirection,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<PlacePreviewResponseDTO> response =
                placeQueryService.getPaginatedPlaces(traits, sortBy, sortDirection, page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/top")
    ResponseEntity<List<PlacePreviewResponseDTO>> getTopPlaces(){
        List<PlacePreviewResponseDTO> response = placeQueryService.getTopPlaces();

        return  ResponseEntity.ok(response);
    }

    @GetMapping("/{placeId}")
    ResponseEntity<PlaceResponseDTO> getPlace(@PathVariable UUID placeId) {
        PlaceResponseDTO response = placeQueryService.getPlaceById(placeId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    ResponseEntity<PlaceResponseDTO> updatePlace(@RequestBody @Valid PlaceRequestDTO dto) {
        PlaceResponseDTO response = placeManagementService.updatePlace(dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{placeId}")
    ResponseEntity<String> deletePlace(@PathVariable UUID placeId) {
        String response = placeManagementService.deletePlace(placeId);

        return ResponseEntity.ok(response);
    }
}