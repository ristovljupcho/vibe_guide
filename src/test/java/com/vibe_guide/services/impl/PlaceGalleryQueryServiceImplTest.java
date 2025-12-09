package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.entities.PlaceGallery;
import com.vibe_guide.repositories.PlaceGalleryRepository;
import com.vibe_guide.repositories.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceGalleryQueryServiceImplTest {

    @Mock
    private PlaceGalleryRepository placeGalleryRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceGalleryQueryServiceImpl service;

    @Test
    void getPlaceGalleryValidatesPlace() {
        UUID placeId = TestDataFactory.uuid();
        PlaceGallery gallery = TestDataFactory.placeGallery(TestDataFactory.uuid(), TestDataFactory.place(placeId));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(TestDataFactory.place(placeId)));
        when(placeGalleryRepository.findAllByPlaceId(placeId)).thenReturn(List.of(gallery));

        List<PlaceGallery> result = service.getPlaceGallery(placeId);

        assertEquals(List.of(gallery), result);
    }
}
