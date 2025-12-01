package com.vibe_guide.controllers;

import com.vibe_guide.dtos.OfferInsertDTO;
import com.vibe_guide.dtos.OfferResponseDTO;
import com.vibe_guide.dtos.OfferUpdateDTO;
import com.vibe_guide.services.OfferManagementService;
import com.vibe_guide.services.OfferQueryService;
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
public class OfferController {
    private final OfferQueryService offerQueryService;
    private final OfferManagementService offerManagementService;

    @GetMapping("/active")
    ResponseEntity<List<OfferResponseDTO>> getAllActiveOffers() {
        List<OfferResponseDTO> offers = offerQueryService.getAllActiveOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/active/{placeId}")
    public ResponseEntity<List<OfferResponseDTO>> getActiveOffersByPlaceId(
            @PathVariable UUID placeId) {
        List<OfferResponseDTO> offers = offerQueryService.getActiveOffersByPlaceId(placeId);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<OfferResponseDTO>> getAllUpcomingOffers() {
        List<OfferResponseDTO> offers = offerQueryService.getAllUpcomingOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/upcoming/{placeId}")
    public ResponseEntity<List<OfferResponseDTO>> getUpcomingOffersByPlaceId(
            @PathVariable UUID placeId) {
        List<OfferResponseDTO> offers = offerQueryService.getUpcomingOffersByPlaceId(placeId);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertDailyOffer(
            @RequestBody @Valid OfferInsertDTO dto) {
        String response = offerManagementService.insertOffer(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDailyOffer(
            @RequestBody @Valid OfferUpdateDTO dto) {
        String response = offerManagementService.updateDailyOffer(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{dailyOfferId}")
    public ResponseEntity<String> deleteDailyOffer(
            @PathVariable UUID dailyOfferId) {
        String msg = offerManagementService.deleteDailyOffer(dailyOfferId);
        return ResponseEntity.ok(msg);
    }
}