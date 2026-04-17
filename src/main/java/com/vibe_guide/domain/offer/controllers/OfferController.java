package com.vibe_guide.domain.offer.controllers;

import com.vibe_guide.domain.offer.dtos.OfferInsertDTO;
import com.vibe_guide.domain.offer.dtos.OfferResponseDTO;
import com.vibe_guide.domain.offer.dtos.OfferUpdateDTO;
import com.vibe_guide.domain.offer.services.OfferManagementService;
import com.vibe_guide.domain.offer.services.OfferQueryService;
import jakarta.validation.Valid;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/offers")
@Validated
public class OfferController {
  private final OfferQueryService offerQueryService;
  private final OfferManagementService offerManagementService;

  @GetMapping("/active")
  ResponseEntity<List<OfferResponseDTO>> getActive() {
    List<OfferResponseDTO> offers = offerQueryService.getActive();
    return ResponseEntity.ok(offers);
  }

  @GetMapping("/active/{placeId}")
  public ResponseEntity<List<OfferResponseDTO>> getActiveByPlaceId(@PathVariable UUID placeId) {
    List<OfferResponseDTO> offers = offerQueryService.getActiveByPlaceId(placeId);
    return ResponseEntity.ok(offers);
  }

  @GetMapping("/upcoming")
  public ResponseEntity<List<OfferResponseDTO>> getUpcoming() {
    List<OfferResponseDTO> offers = offerQueryService.getUpcoming();
    return ResponseEntity.ok(offers);
  }

  @GetMapping("/upcoming/{placeId}")
  public ResponseEntity<List<OfferResponseDTO>> getUpcomingByPlaceId(@PathVariable UUID placeId) {
    List<OfferResponseDTO> offers = offerQueryService.getUpcomingByPlaceId(placeId);
    return ResponseEntity.ok(offers);
  }

  @PostMapping("/insert")
  public ResponseEntity<String> insert(@RequestBody @Valid OfferInsertDTO dto) {
    String response = offerManagementService.insert(dto);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/update")
  public ResponseEntity<String> update(@RequestBody @Valid OfferUpdateDTO dto) {
    String response = offerManagementService.update(dto);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete/{dailyOfferId}")
  public ResponseEntity<String> delete(@PathVariable UUID dailyOfferId) {
    String msg = offerManagementService.delete(dailyOfferId);
    return ResponseEntity.ok(msg);
  }
}

