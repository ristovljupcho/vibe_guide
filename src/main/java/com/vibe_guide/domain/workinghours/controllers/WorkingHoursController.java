package com.vibe_guide.domain.workinghours.controllers;

import com.vibe_guide.domain.workinghours.dtos.WorkingHoursDeleteRequestDTO;
import com.vibe_guide.domain.workinghours.dtos.WorkingHoursRequestDTO;
import com.vibe_guide.domain.workinghours.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.domain.workinghours.services.WorkingHoursManagementService;
import com.vibe_guide.domain.workinghours.services.WorkingHoursQueryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping("/places/{placeId}")
@RestController
public class WorkingHoursController {

  private final WorkingHoursQueryService workingHoursQueryService;
  private final WorkingHoursManagementService workingHoursManagementService;

  @GetMapping("/working-hours")
  public ResponseEntity<List<WorkingHoursResponseDTO>> getAllByPlaceId(@PathVariable UUID placeId) {
    List<WorkingHoursResponseDTO> response = workingHoursQueryService.getAllByPlaceId(placeId);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/working-hours/insert")
  public ResponseEntity<String> insert(
      @PathVariable UUID placeId, @RequestBody @Validated List<WorkingHoursRequestDTO> dtos) {
    String response = workingHoursManagementService.insert(placeId, dtos);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/working-hours/update")
  public ResponseEntity<String> update(
      @PathVariable UUID placeId, @RequestBody @Validated WorkingHoursRequestDTO dto) {
    String response = workingHoursManagementService.update(placeId, dto);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/working-hours/delete")
  public ResponseEntity<String> delete(
      @PathVariable UUID placeId, @RequestBody @Validated WorkingHoursDeleteRequestDTO dto) {
    String response = workingHoursManagementService.delete(placeId, dto);

    return ResponseEntity.ok(response);
  }
}

