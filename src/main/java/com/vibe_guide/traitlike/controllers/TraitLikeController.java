package com.vibe_guide.traitlike.controllers;

import com.vibe_guide.trait.dtos.TraitResponseDTO;
import com.vibe_guide.traitlike.dtos.TraitLikeRequestDTO;
import com.vibe_guide.traitlike.services.TraitLikeManagementService;
import com.vibe_guide.traitlike.services.TraitLikeQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping("/traits")
@RestController
public class TraitLikeController {
  private final TraitLikeManagementService traitLikeManagementService;
  private final TraitLikeQueryService traitLikeQueryService;

  @GetMapping("/likes")
  public ResponseEntity<List<TraitResponseDTO>> findAllByPlaceAndUser(
      @NotNull @RequestParam UUID userId, @NotNull @Valid UUID placeId) {
    List<TraitResponseDTO> result = traitLikeQueryService.findAllByPlaceAndUser(placeId, userId);

    return ResponseEntity.ok(result);
  }

  @PostMapping("/like")
  public ResponseEntity<String> likeTraits(@Validated @RequestBody TraitLikeRequestDTO dto) {
    String result = traitLikeManagementService.likeTraits(dto);

    return ResponseEntity.ok(result);
  }

  @PostMapping("/unlike")
  public ResponseEntity<String> unlikeTraits(@Validated @RequestBody TraitLikeRequestDTO dto) {
    String result = traitLikeManagementService.unlikeTraits(dto);

    return ResponseEntity.ok(result);
  }
}
