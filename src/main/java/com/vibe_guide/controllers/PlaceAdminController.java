package com.vibe_guide.controllers;

import com.vibe_guide.dtos.PlaceAdminRequestDTO;
import com.vibe_guide.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.services.PlaceAdminManagementService;
import com.vibe_guide.services.PlaceAdminQueryService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places/{placeId}")
@Validated
public class PlaceAdminController {

    private final PlaceAdminQueryService placeAdminQueryService;
    private final PlaceAdminManagementService placeAdminManagementService;

    @GetMapping("/admins")
    public ResponseEntity<List<PlaceAdminResponseDTO>> getAdminsForPlace(@PathVariable UUID placeId) {
        List<PlaceAdminResponseDTO> response = placeAdminQueryService.getAllAdminsForPlace(placeId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/admins/insert")
    public ResponseEntity<String> insertAdminsForPlace(@PathVariable UUID placeId,
                                                       @RequestBody @Validated PlaceAdminRequestDTO dto) {
        String response = placeAdminManagementService.insertPlaceAdmin(placeId, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admins/delete/{userId}")
    public ResponseEntity<String> deleteAdminsForPlace(@PathVariable UUID placeId, @PathVariable UUID userId) {
        String response = placeAdminManagementService.deletePlaceAdmin(placeId, userId);

        return ResponseEntity.ok(response);
    }
}
