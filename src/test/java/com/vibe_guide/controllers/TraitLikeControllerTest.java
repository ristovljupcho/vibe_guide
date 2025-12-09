package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.TraitLikeRequestDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.services.TraitLikeManagementService;
import com.vibe_guide.services.TraitLikeQueryService;
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
class TraitLikeControllerTest {

    @Mock
    private TraitLikeManagementService traitLikeManagementService;

    @Mock
    private TraitLikeQueryService traitLikeQueryService;

    @InjectMocks
    private TraitLikeController controller;

    @Test
    void findAllByPlaceAndUserReturnsPayload() {
        UUID placeId = TestDataFactory.uuid();
        UUID userId = TestDataFactory.uuid();
        List<TraitResponseDTO> expected = List.of(TestDataFactory.traitResponseDto(TestDataFactory.uuid()));
        when(traitLikeQueryService.findAllByPlaceAndUser(placeId, userId)).thenReturn(expected);

        ResponseEntity<List<TraitResponseDTO>> response = controller.findAllByPlaceAndUser(userId, placeId);

        assertEquals(expected, response.getBody());
    }

    @Test
    void likeTraitsReturnsMessage() {
        TraitLikeRequestDTO request = TestDataFactory.traitLikeRequestDto(TestDataFactory.uuid(),
                TestDataFactory.uuid(), List.of(TestDataFactory.uuid()));
        when(traitLikeManagementService.likeTraits(request)).thenReturn("ok");

        ResponseEntity<String> response = controller.likeTraits(request);

        assertEquals("ok", response.getBody());
    }

    @Test
    void unlikeTraitsReturnsMessage() {
        TraitLikeRequestDTO request = TestDataFactory.traitLikeRequestDto(TestDataFactory.uuid(),
                TestDataFactory.uuid(), List.of(TestDataFactory.uuid()));
        when(traitLikeManagementService.unlikeTraits(request)).thenReturn("ok");

        ResponseEntity<String> response = controller.unlikeTraits(request);

        assertEquals("ok", response.getBody());
    }
}
