package com.vibe_guide.eventgallery.services.impl;

import com.vibe_guide.eventgallery.services.EventGalleryQueryService;
import com.vibe_guide.event.entities.Event;
import com.vibe_guide.event.repositories.EventRepository;
import com.vibe_guide.eventgallery.entities.EventGallery;
import com.vibe_guide.eventgallery.repositories.EventGalleryRepository;
import com.vibe_guide.exceptions.EventNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventGalleryQueryServiceImpl implements EventGalleryQueryService {
  private final EventGalleryRepository eventGalleryRepository;
  private final EventRepository eventRepository;

  @Override
  public List<EventGallery> getAllByEventId(UUID eventId) {
    Event event =
        eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

    return eventGalleryRepository.findAllByEventId(eventId);
  }
}

