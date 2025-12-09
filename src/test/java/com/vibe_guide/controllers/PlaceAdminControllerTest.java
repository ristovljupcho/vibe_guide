package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.PlaceAdminRequestDTO;
import com.vibe_guide.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.services.PlaceAdminManagementService;
import com.vibe_guide.services.PlaceAdminQueryService;
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
class PlaceAdminControllerTest {

    @Mock
    private PlaceAdminQueryService placeAdminQueryService;

    @Mock
    private PlaceAdminManagementService placeAdminManagementService;

    @InjectMocks
    private PlaceAdminController controller;

    @Test
    void getAdminsReturnsDtos() {
        UUID placeId = TestDataFactory.uuid();
        List<PlaceAdminResponseDTO> expected = List.of(TestDataFactory.placeAdminResponseDto(TestDataFactory.uuid()));
        when(placeAdminQueryService.getAllAdminsForPlace(placeId)).thenReturn(expected);

        ResponseEntity<List<PlaceAdminResponseDTO>> response = controller.getAdminsForPlace(placeId);

        assertEquals(expected, response.getBody());
    }

    @Test
    void insertAdminReturnsMessage() {
        UUID placeId = TestDataFactory.uuid();
        PlaceAdminRequestDTO dto = TestDataFactory.placeAdminRequestDto(TestDataFactory.uuid());
        when(placeAdminManagementService.insertPlaceAdmin(placeId, dto)).thenReturn("created");

        ResponseEntity<String> response = controller.insertAdminsForPlace(placeId, dto);

        assertEquals("created", response.getBody());
    }

    @Test
    void deleteAdminReturnsMessage() {
        UUID placeId = TestDataFactory.uuid();
        UUID userId = TestDataFactory.uuid();
        when(placeAdminManagementService.deletePlaceAdmin(placeId, userId)).thenReturn("deleted");

        ResponseEntity<String> response = controller.deleteAdminsForPlace(placeId, userId);

        assertEquals("deleted", response.getBody());
    }
}
