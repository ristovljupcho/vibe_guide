package com.vibe_guide.domain.review.controllers;

import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.domain.review.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.domain.review.dtos.ReviewResponseDTO;
import com.vibe_guide.domain.review.dtos.ReviewUpdateRequestDTO;
import com.vibe_guide.domain.review.services.ReviewManagementService;
import com.vibe_guide.domain.review.services.ReviewQueryService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Validated
public class ReviewController {

  private final ReviewQueryService reviewQueryService;
  private final ReviewManagementService reviewManagementService;

  @GetMapping("/{placeId}")
  ResponseEntity<Page<ReviewResponseDTO>> getPaginatedByPlaceId(
      @PathVariable UUID placeId,
      @RequestParam(required = false) ReviewSortBy sortBy,
      @RequestParam(required = false) SortDirection sortDirection,
      @RequestParam Integer page,
      @RequestParam Integer size) {
    Page<ReviewResponseDTO> response =
        reviewQueryService.getPaginatedByPlaceId(placeId, sortBy, sortDirection, page, size);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{placeId}/top")
  ResponseEntity<List<ReviewResponseDTO>> getTopFiveByPlaceId(@PathVariable UUID placeId) {
    List<ReviewResponseDTO> response = reviewQueryService.getTopFiveByPlaceId(placeId);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/insert")
  ResponseEntity<String> insert(@RequestBody @Valid ReviewInsertRequestDTO reviewInsertRequestDTO) {
    String response = reviewManagementService.insert(reviewInsertRequestDTO);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/update")
  ResponseEntity<String> update(@RequestBody @Valid ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
    String response = reviewManagementService.update(reviewUpdateRequestDTO);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{reviewId}/delete")
  ResponseEntity<String> delete(@PathVariable UUID reviewId) {
    String response = reviewManagementService.delete(reviewId);

    return ResponseEntity.ok(response);
  }
}

