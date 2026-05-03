package com.vibe_guide.domain.traitlike.controllers;

import com.vibe_guide.domain.trait.dtos.TraitResponseDTO;
import com.vibe_guide.domain.traitlike.dtos.TraitLikeRequestDTO;
import com.vibe_guide.domain.traitlike.services.TraitLikeManagementService;
import com.vibe_guide.domain.traitlike.services.TraitLikeQueryService;
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
  public ResponseEntity<List<TraitResponseDTO>> getAllByPlaceIdAndUserId(
      @NotNull @RequestParam String userId, @NotNull @RequestParam UUID placeId) {
    List<TraitResponseDTO> result = traitLikeQueryService.getAllByPlaceIdAndUserId(placeId, userId);

    return ResponseEntity.ok(result);
  }

  @PostMapping("/like")
  public ResponseEntity<String> insert(@Validated @RequestBody TraitLikeRequestDTO dto) {
    String result = traitLikeManagementService.insert(dto);

    return ResponseEntity.ok(result);
  }

  @PostMapping("/unlike")
  public ResponseEntity<String> delete(@Validated @RequestBody TraitLikeRequestDTO dto) {
    String result = traitLikeManagementService.delete(dto);

    return ResponseEntity.ok(result);
  }
}

