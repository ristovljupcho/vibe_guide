package com.vibe_guide.domain.wishlistplace.controllers;

import com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceToggleRequestDTO;
import com.vibe_guide.domain.wishlistplace.services.WishlistPlaceService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist-places")
@RequiredArgsConstructor
public class WishlistPlaceController {

  private final WishlistPlaceService wishlistService;

  @GetMapping("/{userId}")
  public ResponseEntity<List<WishlistPlaceResponseDTO>> getAllByUserId(@PathVariable UUID userId) {
    return ResponseEntity.ok(wishlistService.getAllByUserId(userId));
  }

  @PostMapping("/toggle")
  public ResponseEntity<String> toggle(@RequestBody @Valid WishlistPlaceToggleRequestDTO dto) {
    return ResponseEntity.ok(wishlistService.toggle(dto));
  }
}

