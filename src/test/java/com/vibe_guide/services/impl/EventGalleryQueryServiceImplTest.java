package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.EventGallery;
import com.vibe_guide.repositories.EventGalleryRepository;
import com.vibe_guide.repositories.EventRepository;
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
class EventGalleryQueryServiceImplTest {

    @Mock
    private EventGalleryRepository eventGalleryRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventGalleryQueryServiceImpl service;

    @Test
    void getEventGalleryValidatesEvent() {
        UUID eventId = TestDataFactory.uuid();
        Event event = TestDataFactory.event(eventId, TestDataFactory.place(TestDataFactory.uuid()));
        EventGallery gallery = TestDataFactory.eventGallery(TestDataFactory.uuid(), event);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventGalleryRepository.findAllByEventId(eventId)).thenReturn(List.of(gallery));

        List<EventGallery> result = service.getEventGallery(eventId);

        assertEquals(List.of(gallery), result);
    }
}
