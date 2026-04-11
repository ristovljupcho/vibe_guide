package com.vibe_guide.wishlistplace.controllers;

import com.vibe_guide.wishlistplace.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.wishlistplace.services.WishlistPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wishlist-places")
@RequiredArgsConstructor
public class WishlistPlaceController {

    private final WishlistPlaceService wishlistService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<WishlistPlaceResponseDTO>> getWishlistPlacesByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(wishlistService.getWishlistPlacesByUserId(userId));
    }

    @PostMapping("/{userId}/{placeId}")
    public ResponseEntity<String> manageWishlistPlace(
            @PathVariable UUID userId,
            @PathVariable UUID placeId
    ) {
        return ResponseEntity.ok(wishlistService.manageWishlistPlace(userId, placeId));
    }
}
