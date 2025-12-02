package com.vibe_guide.controllers;

import com.vibe_guide.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.services.FavouritePlaceService;
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
@RequestMapping("/favourite-places")
@Validated
public class FavouritePlaceController {

    private final FavouritePlaceService favouritePlaceService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavouritePlaceResponseDTO>> getFavouritePlacesByUserId(
            @PathVariable UUID userId
    ) {
        List<FavouritePlaceResponseDTO> response = favouritePlaceService.getFavouritePlacesByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/{placeId}")
    public ResponseEntity<String> manageFavouritePlace(
            @PathVariable UUID userId,
            @PathVariable UUID placeId
    ) {
        String message = favouritePlaceService.manageFavouritePlace(userId, placeId);
        return ResponseEntity.ok(message);
    }
}
