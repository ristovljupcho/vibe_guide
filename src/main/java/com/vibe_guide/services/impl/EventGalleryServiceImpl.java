package com.vibe_guide.services.impl;

import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.EventGallery;
import com.vibe_guide.exceptions.EventNotFoundException;
import com.vibe_guide.repositories.EventGalleryRepository;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.services.EventGalleryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventGalleryServiceImpl implements EventGalleryService {
    private final EventGalleryRepository eventGalleryRepository;
    private final EventRepository eventRepository;

    @Override
    public List<EventGallery> getEventGallery(UUID eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new EventNotFoundException(eventId));

        return eventGalleryRepository.findByEventId(eventId);
    }
}
