package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.PlaceAdminRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.PlaceAdmin;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.composite_keys.PlaceAdminId;
import com.vibe_guide.repositories.PlaceAdminRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.utils.PlaceAdminResponseMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceAdminManagementServiceImplTest {

    @Mock
    private PlaceAdminRepository placeAdminRepository;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PlaceAdminManagementServiceImpl service;

    @Test
    void insertPlaceAdminSavesEntity() {
        UUID placeId = TestDataFactory.uuid();
        UUID userId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        User user = TestDataFactory.user(userId);
        PlaceAdminRequestDTO dto = TestDataFactory.placeAdminRequestDto(userId);
        PlaceAdminId id = new PlaceAdminId(placeId, userId);
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(placeAdminRepository.existsById(id)).thenReturn(false);

        String result = service.insertPlaceAdmin(placeId, dto);

        assertEquals(String.format(PlaceAdminResponseMessages.PLACE_ADMIN_INSERT_MESSAGE, userId, placeId), result);
        verify(placeAdminRepository).save(any(PlaceAdmin.class));
    }

    @Test
    void deletePlaceAdminRemovesEntry() {
        UUID placeId = TestDataFactory.uuid();
        UUID userId = TestDataFactory.uuid();
        PlaceAdminId id = new PlaceAdminId(placeId, userId);
        when(placeAdminRepository.existsById(id)).thenReturn(true);

        String result = service.deletePlaceAdmin(placeId, userId);

        assertEquals(String.format(PlaceAdminResponseMessages.PLACE_ADMIN_DELETE_MESSAGE, userId, placeId), result);
        verify(placeAdminRepository).deleteById(id);
    }
}
