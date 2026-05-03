package com.vibe_guide.domain.placeadmin.controllers;

import com.vibe_guide.domain.placeadmin.dtos.PlaceAdminRequestDTO;
import com.vibe_guide.domain.placeadmin.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.domain.placeadmin.services.PlaceAdminManagementService;
import com.vibe_guide.domain.placeadmin.services.PlaceAdminQueryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places/{placeId}")
@Validated
public class PlaceAdminController {

  private final PlaceAdminQueryService placeAdminQueryService;
  private final PlaceAdminManagementService placeAdminManagementService;

  @GetMapping("/admins")
  public ResponseEntity<List<PlaceAdminResponseDTO>> getAllByPlaceId(@PathVariable UUID placeId) {
    List<PlaceAdminResponseDTO> response = placeAdminQueryService.getAllByPlaceId(placeId);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/admins/insert")
  public ResponseEntity<String> insert(
      @PathVariable UUID placeId, @RequestBody @Validated PlaceAdminRequestDTO dto) {
    String response = placeAdminManagementService.insert(placeId, dto);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/admins/delete/{userId}")
  public ResponseEntity<String> delete(@PathVariable UUID placeId, @PathVariable String userId) {
    String response = placeAdminManagementService.delete(placeId, userId);

    return ResponseEntity.ok(response);
  }
}

