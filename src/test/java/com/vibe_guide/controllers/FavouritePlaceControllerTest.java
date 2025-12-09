package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.services.FavouritePlaceService;
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
class FavouritePlaceControllerTest {

    @Mock
    private FavouritePlaceService favouritePlaceService;

    @InjectMocks
    private FavouritePlaceController controller;

    @Test
    void getFavouritePlacesReturnsDtos() {
        UUID userId = TestDataFactory.uuid();
        List<FavouritePlaceResponseDTO> expected = List.of(
                TestDataFactory.favouritePlaceResponseDto(TestDataFactory.uuid()));
        when(favouritePlaceService.getFavouritePlacesByUserId(userId)).thenReturn(expected);

        ResponseEntity<List<FavouritePlaceResponseDTO>> response = controller.getFavouritePlacesByUserId(userId);

        assertEquals(expected, response.getBody());
    }

    @Test
    void manageFavouritePlaceReturnsMessage() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        when(favouritePlaceService.manageFavouritePlace(userId, placeId)).thenReturn("toggled");

        ResponseEntity<String> response = controller.manageFavouritePlace(userId, placeId);

        assertEquals("toggled", response.getBody());
    }
}
