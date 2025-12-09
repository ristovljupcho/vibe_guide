package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.services.PlaceTraitManagementService;
import com.vibe_guide.services.PlaceTraitQueryService;
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
class PlaceTraitControllerTest {

    @Mock
    private PlaceTraitQueryService placeTraitQueryService;

    @Mock
    private PlaceTraitManagementService placeTraitManagementService;

    @InjectMocks
    private PlaceTraitController controller;

    @Test
    void getTraitsForCarouselReturnsDtos() {
        UUID placeId = TestDataFactory.uuid();
        List<TraitCarouselResponseDTO> expected = List.of(TestDataFactory.traitCarouselResponseDto("Trait"));
        when(placeTraitQueryService.getTraitsForDisplayInPlaceCarousel(placeId)).thenReturn(expected);

        ResponseEntity<List<TraitCarouselResponseDTO>> response = controller.getTraitsForCarousel(placeId);

        assertEquals(expected, response.getBody());
    }

    @Test
    void insertTraitForPlaceReturnsMessage() {
        PlaceTraitRequestDTO dto = TestDataFactory.placeTraitRequestDto(TestDataFactory.uuid(), TestDataFactory.uuid());
        when(placeTraitManagementService.insertSingleTraitInPlace(dto)).thenReturn("ok");

        ResponseEntity<String> response = controller.insertTraitForPlace(dto);

        assertEquals("ok", response.getBody());
    }

    @Test
    void batchDeleteTraitsReturnsMessage() {
        BatchDeleteTraitsInPlace request = TestDataFactory.batchDeleteTraitsRequest(TestDataFactory.uuid(),
                List.of(TestDataFactory.uuid()));
        when(placeTraitManagementService.batchDeleteTraitsInPlace(request)).thenReturn("deleted");

        ResponseEntity<String> response = controller.deleteTraitForPlace(request);

        assertEquals("deleted", response.getBody());
    }
}
