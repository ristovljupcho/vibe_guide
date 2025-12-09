package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.EventGallery;
import com.vibe_guide.repositories.EventGalleryRepository;
import com.vibe_guide.repositories.EventRepository;
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
class EventGalleryManagementServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventGalleryRepository eventGalleryRepository;

    @InjectMocks
    private EventGalleryManagementServiceImpl service;

    @Test
    void addImagesToEventPersistsValidImages() {
        UUID eventId = TestDataFactory.uuid();
        Event event = TestDataFactory.event(eventId, TestDataFactory.place(TestDataFactory.uuid()));
        MultipartFile validImage = TestDataFactory.mockImage("event.png");
        MultipartFile invalidImage = new MockMultipartFile("ignored.txt", "ignored.txt", "text/plain", "data".getBytes());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        service.addImagesToEvent(eventId, List.of(validImage, invalidImage));

        ArgumentCaptor<List<EventGallery>> captor = ArgumentCaptor.forClass(List.class);
        verify(eventGalleryRepository).saveAll(captor.capture());
        assertEquals(1, captor.getValue().size());
        assertEquals(event, captor.getValue().getFirst().getEvent());
    }

    @Test
    void deleteImageFromEventDelegatesToRepository() {
        UUID imageId = TestDataFactory.uuid();
        when(eventGalleryRepository.existsById(imageId)).thenReturn(true);

        service.deleteImageFromEvent(imageId);

        verify(eventGalleryRepository).deleteById(imageId);
    }
}
