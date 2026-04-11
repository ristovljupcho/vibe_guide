package com.vibe_guide.eventgallery.services;

import com.vibe_guide.eventgallery.entities.EventGallery;
import java.util.List;
import java.util.UUID;

public interface EventGalleryQueryService {
  List<EventGallery> getEventGallery(UUID eventId);
}
