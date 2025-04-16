package com.vibe_guide.controllers;

import com.vibe_guide.dtos.WorkingHoursDeleteRequestDTO;
import com.vibe_guide.dtos.WorkingHoursRequestDTO;
import com.vibe_guide.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.services.WorkingHoursManagementService;
import com.vibe_guide.services.WorkingHoursQueryService;
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

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RequestMapping("/places/{placeId}")
@RestController
public class WorkingHoursController {

    private final WorkingHoursQueryService workingHoursQueryService;
    private final WorkingHoursManagementService workingHoursManagementService;

    @GetMapping("/working-hours")
    public ResponseEntity<List<WorkingHoursResponseDTO>> getWeeklyWorkingHoursForPlace(@PathVariable UUID placeId) {
        List<WorkingHoursResponseDTO> response = workingHoursQueryService.getWeeklyWorkingHoursForPlace(placeId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/working-hours/insert")
    public ResponseEntity<String> insertWorkingHoursForPlace(@PathVariable UUID placeId,
                                                             @RequestBody @Validated
                                                             List<WorkingHoursRequestDTO> dtos) {
        String response = workingHoursManagementService.insertWorkingHoursForPlace(placeId, dtos);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/working-hours/update")
    public ResponseEntity<String> updateDailyWorkingHoursForPlace(@PathVariable UUID placeId,
                                                                  @RequestBody @Validated WorkingHoursRequestDTO dto) {
        String response = workingHoursManagementService.updateDailyWorkingHoursForPlace(placeId, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/working-hours/delete")
    public ResponseEntity<String> updateDailyWorkingHoursForPlace(@PathVariable UUID placeId,
                                                                  @RequestBody @Validated
                                                                  WorkingHoursDeleteRequestDTO dto) {
        String response = workingHoursManagementService.deleteWorkingHoursForPlace(placeId, dto);

        return ResponseEntity.ok(response);
    }
}