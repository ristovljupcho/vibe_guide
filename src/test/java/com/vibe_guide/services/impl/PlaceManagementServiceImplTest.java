package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.PlaceRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.utils.PlaceResponseMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceManagementServiceImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceManagementServiceImpl service;

    @Test
    void updatePlaceUpdatesEntityFields() {
        Place place = TestDataFactory.place(TestDataFactory.uuid());
        PlaceRequestDTO request = TestDataFactory.placeRequestDto(place.getId());
        when(placeRepository.findById(place.getId())).thenReturn(Optional.of(place));

        String result = service.updatePlace(request);

        assertEquals(String.format(PlaceResponseMessages.PLACE_UPDATE_MESSAGE, request.name()), result);
        verify(placeRepository).save(place);
    }

    @Test
    void deletePlaceDeletesEntity() {
        UUID placeId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));

        String result = service.deletePlace(placeId);

        assertEquals(String.format(PlaceResponseMessages.PLACE_DELETE_MESSAGE, placeId, place.getName()), result);
        verify(placeRepository).deleteById(placeId);
    }
}
