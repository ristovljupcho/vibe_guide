package com.vibe_guide.controllers;

import com.vibe_guide.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.dtos.ReviewResponseDTO;
import com.vibe_guide.dtos.ReviewUpdateRequestDTO;
import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.services.ReviewManagementService;
import com.vibe_guide.services.ReviewQueryService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Validated
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewManagementService reviewManagementService;

    @GetMapping("/{placeId}")
    ResponseEntity<Page<ReviewResponseDTO>> getPaginatedReviewsForPlace(
            @PathVariable UUID placeId,
            @RequestParam(required = false) ReviewSortBy sortBy,
            @RequestParam(required = false) SortDirection sortDirection,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        Page<ReviewResponseDTO> response =
                reviewQueryService.getPaginatedReviews(placeId, sortBy, sortDirection, page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{placeId}/top")
    ResponseEntity<List<ReviewResponseDTO>> getTopReviews(@PathVariable UUID placeId) {
        List<ReviewResponseDTO> response = reviewQueryService.getTopFiveReviews(placeId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/insert")
    ResponseEntity<String> insertReview(@RequestBody @Valid ReviewInsertRequestDTO reviewInsertRequestDTO) {
        String response = reviewManagementService.insertReview(reviewInsertRequestDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    ResponseEntity<String> updateReview(@RequestBody @Valid ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        String response = reviewManagementService.updateReview(reviewUpdateRequestDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{placeId}/delete")
    ResponseEntity<String> deleteReview(@PathVariable UUID placeId) {
        String response = reviewManagementService.deleteReview(placeId);

        return ResponseEntity.ok(response);
    }
}
