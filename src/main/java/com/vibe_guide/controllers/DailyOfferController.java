package com.vibe_guide.controllers;

import com.vibe_guide.dtos.DailyOfferInsertDTO;
import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.dtos.DailyOfferUpdateDTO;
import com.vibe_guide.services.DailyOfferManagementService;
import com.vibe_guide.services.DailyOfferQueryService;
import jakarta.validation.Valid;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily-offers")
@Validated
public class DailyOfferController {
    private final DailyOfferQueryService dailyOfferQueryService;
    private final DailyOfferManagementService dailyOfferManagementService;

    @GetMapping
    ResponseEntity<List<DailyOfferResponseDTO>> getTodayDailyOffers() {
        List<DailyOfferResponseDTO> offers = dailyOfferQueryService.getTodayDailyOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/place/{placeId}")
    public ResponseEntity<List<DailyOfferResponseDTO>> getTodayDailyOffersByPlaceId(
            @PathVariable UUID placeId) {
        List<DailyOfferResponseDTO> offers = dailyOfferQueryService.getTodayDailyOffersByPlaceId(placeId);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/place/{placeId}/insert")
    public ResponseEntity<String> insertDailyOffer(
            @PathVariable UUID placeId,
            @RequestBody @Valid DailyOfferInsertDTO dto) {
        String msg = dailyOfferManagementService.insertDailyOffer(placeId, dto);
        return ResponseEntity.ok(msg);
    }

    @PutMapping("/place/{placeId}/daily-offer/{dailyOfferId}")
    public ResponseEntity<String> updateDailyOffer(
            @PathVariable UUID placeId,
            @PathVariable UUID dailyOfferId,
            @RequestBody @Valid DailyOfferUpdateDTO dto) {
        String msg = dailyOfferManagementService.updateDailyOffer(placeId, dailyOfferId, dto);
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/{dailyOfferId}")
    public ResponseEntity<String> deleteDailyOffer(
            @PathVariable UUID dailyOfferId) {
        String msg = dailyOfferManagementService.deleteDailyOffer(dailyOfferId);
        return ResponseEntity.ok(msg);
    }
}