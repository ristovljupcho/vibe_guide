package com.vibe_guide.services.impl;

import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.EventGallery;
import com.vibe_guide.exceptions.EventNotFoundException;
import com.vibe_guide.repositories.EventGalleryRepository;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.services.EventGalleryQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventGalleryQueryServiceImpl implements EventGalleryQueryService {
    private final EventGalleryRepository eventGalleryRepository;
    private final EventRepository eventRepository;

    @Override
    public List<EventGallery> getEventGallery(UUID eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new EventNotFoundException(eventId));

        return eventGalleryRepository.findAllByEventId(eventId);
    }
}