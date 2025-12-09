package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.services.VisitedPlaceService;
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
class VisitedPlaceControllerTest {

    @Mock
    private VisitedPlaceService visitedPlaceService;

    @InjectMocks
    private VisitedPlaceController controller;

    @Test
    void getVisitedPlacesReturnsDtos() {
        UUID userId = TestDataFactory.uuid();
        List<VisitedPlaceResponseDTO> expected = List.of(TestDataFactory.visitedPlaceResponseDto(TestDataFactory.uuid()));
        when(visitedPlaceService.getVisitedPlacesByUserId(userId)).thenReturn(expected);

        ResponseEntity<List<VisitedPlaceResponseDTO>> response = controller.getVisitedPlacesByUserId(userId);

        assertEquals(expected, response.getBody());
    }

    @Test
    void manageVisitedPlaceReturnsMessage() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        when(visitedPlaceService.manageVisitedPlace(userId, placeId)).thenReturn("toggled");

        ResponseEntity<String> response = controller.manageVisitedPlace(userId, placeId);

        assertEquals("toggled", response.getBody());
    }
}
