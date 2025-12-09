package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.PlaceGallery;
import com.vibe_guide.repositories.PlaceGalleryRepository;
import com.vibe_guide.repositories.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceGalleryManagementServiceImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceGalleryRepository placeGalleryRepository;

    @InjectMocks
    private PlaceGalleryManagementServiceImpl service;

    @Test
    void addImagesToPlacePersistsImageBytes() {
        UUID placeId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        MultipartFile validImage = TestDataFactory.mockImage("gallery.png");
        MultipartFile invalid = new MockMultipartFile("doc.txt", "doc.txt", "text/plain", "data".getBytes());
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));

        service.addImagesToPlace(placeId, List.of(validImage, invalid));

        ArgumentCaptor<List<PlaceGallery>> captor = ArgumentCaptor.forClass(List.class);
        verify(placeGalleryRepository).saveAll(captor.capture());
        assertEquals(1, captor.getValue().size());
        assertEquals(place, captor.getValue().getFirst().getPlace());
    }

    @Test
    void deleteImageFromPlaceRemovesWhenExists() {
        UUID imageId = TestDataFactory.uuid();
        when(placeGalleryRepository.existsById(imageId)).thenReturn(true);

        service.deleteImageFromPlace(imageId);

        verify(placeGalleryRepository).deleteById(imageId);
    }

    @Test
    void deleteAllImagesFromPlaceCallsRepository() {
        UUID placeId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        List<PlaceGallery> galleries = List.of(TestDataFactory.placeGallery(TestDataFactory.uuid(), place));
        when(placeGalleryRepository.findAllByPlaceId(placeId)).thenReturn(galleries);

        service.deleteALlImagesFromPlace(placeId);

        verify(placeGalleryRepository).deleteAll(galleries);
    }
}
