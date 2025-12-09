package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceRequestDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.services.PlaceManagementService;
import com.vibe_guide.services.PlaceQueryService;
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
class PlaceControllerTest {

    @Mock
    private PlaceQueryService placeQueryService;

    @Mock
    private PlaceManagementService placeManagementService;

    @InjectMocks
    private PlaceController controller;

    @Test
    void getPlacesReturnsDtos() {
        List<PlacePreviewResponseDTO> expected = List.of(TestDataFactory.placePreviewResponseDto(TestDataFactory.uuid()));
        when(placeQueryService.getPlaces(null, PlaceSortBy.DEFAULT, SortDirection.ASC)).thenReturn(expected);

        ResponseEntity<List<PlacePreviewResponseDTO>> response =
                controller.getPlaces(null, PlaceSortBy.DEFAULT, SortDirection.ASC);

        assertEquals(expected, response.getBody());
    }

    @Test
    void getPlaceReturnsDetails() {
        UUID placeId = TestDataFactory.uuid();
        PlaceResponseDTO dto = TestDataFactory.placeResponseDto();
        when(placeQueryService.getPlaceById(placeId)).thenReturn(dto);

        ResponseEntity<PlaceResponseDTO> response = controller.getPlace(placeId);

        assertEquals(dto, response.getBody());
    }

    @Test
    void updatePlaceReturnsMessage() {
        PlaceRequestDTO dto = TestDataFactory.placeRequestDto(TestDataFactory.uuid());
        when(placeManagementService.updatePlace(dto)).thenReturn("updated");

        ResponseEntity<String> response = controller.updatePlace(dto);

        assertEquals("updated", response.getBody());
    }

    @Test
    void deletePlaceReturnsMessage() {
        UUID placeId = TestDataFactory.uuid();
        when(placeManagementService.deletePlace(placeId)).thenReturn("deleted");

        ResponseEntity<String> response = controller.deletePlace(placeId);

        assertEquals("deleted", response.getBody());
    }
}
