package com.vibe_guide.domain.event.mappers;

import com.vibe_guide.domain.event.dtos.EventResponseDTO;
import com.vibe_guide.domain.event.entities.Event;
import com.vibe_guide.domain.eventgallery.entities.EventGallery;
import com.vibe_guide.domain.place.entities.Place;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
  public EventResponseDTO toEventResponseDTO(Event event) {
    String name = event.getName();
    Place place = event.getPlace();
    String placeName = place.getName();
    String description = event.getDescription();
    LocalDateTime startDate = event.getStartDate();
    LocalDateTime endDate = event.getEndDate();

    List<EventGallery> gallery = event.getGalleries().stream().toList();
    String imageUrl;
    if (gallery.isEmpty()) {
      imageUrl = null;
    } else {
      EventGallery eventGallery = gallery.getFirst();
      imageUrl = eventGallery.getPhoto();
    }

    return new EventResponseDTO(name, placeName, description, startDate, endDate, imageUrl);
  }
}

