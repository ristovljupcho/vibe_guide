package com.vibe_guide.controllers;

import com.vibe_guide.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.services.VisitedPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visited-places")
@Validated
public class VisitedPlaceController {
    private final VisitedPlaceService visitedPlaceService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<VisitedPlaceResponseDTO>> getVisitedPlacesByUserId(@PathVariable UUID userId) {
        List<VisitedPlaceResponseDTO> response = visitedPlaceService.getVisitedPlacesByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/{placeId}")
    public ResponseEntity<String> manageVisitedPlace(
            @PathVariable UUID userId,
            @PathVariable UUID placeId
    ) {
        String result = visitedPlaceService.manageVisitedPlace(userId, placeId);
        return ResponseEntity.ok(result);
    }
}
