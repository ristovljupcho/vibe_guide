package com.vibe_guide.controllers;

import com.vibe_guide.dtos.TraitInsertRequestDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.dtos.TraitUpdateRequestDTO;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.TraitSortBy;
import com.vibe_guide.services.TraitManagementService;
import com.vibe_guide.services.TraitQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RequestMapping("/traits")
@RestController
public class TraitController {

    private final TraitQueryService traitQueryService;
    private final TraitManagementService traitManagementService;

    @GetMapping
    ResponseEntity<Page<TraitResponseDTO>> getPaginatedTraits(@RequestParam(required = false) TraitType traitType,
                                                              @RequestParam(required = false) TraitSortBy sortBy,
                                                              @RequestParam(required = false)
                                                              SortDirection sortDirection,
                                                              @RequestParam Integer page,
                                                              @RequestParam Integer size) {
        Page<TraitResponseDTO> response =
                traitQueryService.getPaginatedTraits(traitType, sortBy, sortDirection, page, size);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert")
    ResponseEntity<String> insertTrait(@Valid @RequestBody TraitInsertRequestDTO dto) {
        String response = traitManagementService.insertTrait(dto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    ResponseEntity<String> updateTrait(@Valid @RequestBody TraitUpdateRequestDTO dto) {
        String response = traitManagementService.updateTrait(dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{traitId}")
    ResponseEntity<String> deleteTrait(@PathVariable UUID traitId) {
        String response = traitManagementService.deleteTrait(traitId);

        return ResponseEntity.ok(response);
    }
}