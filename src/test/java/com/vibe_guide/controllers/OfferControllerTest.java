package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.OfferInsertDTO;
import com.vibe_guide.dtos.OfferResponseDTO;
import com.vibe_guide.services.OfferManagementService;
import com.vibe_guide.services.OfferQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferControllerTest {

    @Mock
    private OfferQueryService offerQueryService;

    @Mock
    private OfferManagementService offerManagementService;

    @InjectMocks
    private OfferController controller;

    @Test
    void getAllActiveOffersReturnsDtos() {
        List<OfferResponseDTO> offers = List.of(TestDataFactory.offerResponseDto());
        when(offerQueryService.getAllActiveOffers()).thenReturn(offers);

        ResponseEntity<List<OfferResponseDTO>> response = controller.getAllActiveOffers();

        assertEquals(offers, response.getBody());
    }

    @Test
    void insertDailyOfferReturnsMessage() {
        OfferInsertDTO dto = TestDataFactory.offerInsertDto(TestDataFactory.uuid());
        when(offerManagementService.insertOffer(dto)).thenReturn("created");

        ResponseEntity<String> response = controller.insertDailyOffer(dto);

        assertEquals("created", response.getBody());
    }

    @Test
    void deleteDailyOfferReturnsMessage() {
        UUID offerId = TestDataFactory.uuid();
        when(offerManagementService.deleteDailyOffer(offerId)).thenReturn("deleted");

        ResponseEntity<String> response = controller.deleteDailyOffer(offerId);

        assertEquals("deleted", response.getBody());
    }
}
