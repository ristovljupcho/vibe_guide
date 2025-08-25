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
@RequestMapping("/offers")
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
        List<DailyOfferResponseDTO> offers = dailyOfferQueryService.getTodayOffersByPlaceId(placeId);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/upcoming/{placeId}")
    public ResponseEntity<List<DailyOfferResponseDTO>> getUpcomingOffersByPlaceId(
            @PathVariable UUID placeId) {
        List<DailyOfferResponseDTO> offers = dailyOfferQueryService.findUpcomingOffersByPlaceId(placeId);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertDailyOffer(
            @RequestBody @Valid DailyOfferInsertDTO dto) {
        String response = dailyOfferManagementService.insertOffer(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDailyOffer(
            @RequestBody @Valid DailyOfferUpdateDTO dto) {
        String response = dailyOfferManagementService.updateDailyOffer(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{dailyOfferId}")
    public ResponseEntity<String> deleteDailyOffer(
            @PathVariable UUID dailyOfferId) {
        String msg = dailyOfferManagementService.deleteDailyOffer(dailyOfferId);
        return ResponseEntity.ok(msg);
    }
}