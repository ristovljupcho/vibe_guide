package com.vibe_guide.domain.favouriteplace.controllers;

import com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceToggleRequestDTO;
import com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.domain.favouriteplace.services.FavouritePlaceService;
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
@RequestMapping("/favourite-places")
@Validated
public class FavouritePlaceController {

  private final FavouritePlaceService favouritePlaceService;

  @GetMapping("/{userId}")
  public ResponseEntity<List<FavouritePlaceResponseDTO>> getAllByUserId(@PathVariable String userId) {
    List<FavouritePlaceResponseDTO> response = favouritePlaceService.getAllByUserId(userId);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/toggle")
  public ResponseEntity<String> toggle(@RequestBody @Valid FavouritePlaceToggleRequestDTO dto) {
    String message = favouritePlaceService.toggle(dto);
    return ResponseEntity.ok(message);
  }
}

