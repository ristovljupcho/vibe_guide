package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.services.WishlistPlaceService;
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
class WishlistPlaceControllerTest {

    @Mock
    private WishlistPlaceService wishlistService;

    @InjectMocks
    private WishlistPlaceController controller;

    @Test
    void getWishlistPlacesReturnsDtos() {
        UUID userId = TestDataFactory.uuid();
        List<WishlistPlaceResponseDTO> expected = List.of(
                TestDataFactory.wishlistPlaceResponseDto(TestDataFactory.uuid()));
        when(wishlistService.getWishlistPlacesByUserId(userId)).thenReturn(expected);

        ResponseEntity<List<WishlistPlaceResponseDTO>> response = controller.getWishlistPlacesByUserId(userId);

        assertEquals(expected, response.getBody());
    }

    @Test
    void manageWishlistPlaceReturnsMessage() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        when(wishlistService.manageWishlistPlace(userId, placeId)).thenReturn("toggled");

        ResponseEntity<String> response = controller.manageWishlistPlace(userId, placeId);

        assertEquals("toggled", response.getBody());
    }
}
