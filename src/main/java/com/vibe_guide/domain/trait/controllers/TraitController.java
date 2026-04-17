package com.vibe_guide.domain.trait.controllers;

import com.vibe_guide.enums.TraitType;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.TraitSortBy;
import com.vibe_guide.domain.trait.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.domain.trait.dtos.TraitInsertRequestDTO;
import com.vibe_guide.domain.trait.dtos.TraitResponseDTO;
import com.vibe_guide.domain.trait.dtos.TraitUpdateRequestDTO;
import com.vibe_guide.domain.trait.services.TraitManagementService;
import com.vibe_guide.domain.trait.services.TraitQueryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
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

@Validated
@RequiredArgsConstructor
@RequestMapping("/traits")
@RestController
public class TraitController {

  private final TraitQueryService traitQueryService;
  private final TraitManagementService traitManagementService;

  @GetMapping("/paginated")
  ResponseEntity<Page<TraitResponseDTO>> getPaginated(
      @RequestParam(required = false) TraitType traitType,
      @RequestParam(required = false) TraitSortBy sortBy,
      @RequestParam(required = false) SortDirection sortDirection,
      @RequestParam Integer page,
      @RequestParam Integer size) {
    Page<TraitResponseDTO> response =
        traitQueryService.getPaginated(traitType, sortBy, sortDirection, page, size);

    return ResponseEntity.ok(response);
  }

  @GetMapping
  ResponseEntity<List<TraitCarouselResponseDTO>> getAll() {
    List<TraitCarouselResponseDTO> response = traitQueryService.getAll();

    return ResponseEntity.ok(response);
  }

  @PostMapping("/insert")
  ResponseEntity<String> insert(@Valid @RequestBody TraitInsertRequestDTO dto) {
    String response = traitManagementService.insert(dto);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/update")
  ResponseEntity<String> update(@Valid @RequestBody TraitUpdateRequestDTO dto) {
    String response = traitManagementService.update(dto);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete/{traitId}")
  ResponseEntity<String> delete(@PathVariable UUID traitId) {
    String response = traitManagementService.delete(traitId);

    return ResponseEntity.ok(response);
  }
}

