package com.vibe_guide.event.mappers;

import com.vibe_guide.event.dtos.EventResponseDTO;
import com.vibe_guide.event.entities.Event;
import com.vibe_guide.eventgallery.entities.EventGallery;
import com.vibe_guide.place.entities.Place;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 *
 * <ul>
 *   <li>This class is responsible for providing conversion methods from {@link Event} entity to
 *       Data Transfer Objects and vice versa.
 * </ul>
 */
@Component
public class EventMapper {

  /**
   * Converts a {@link Event} entity to a {@link EventResponseDTO} entity.
   *
   * @param event {@link Event} entity to convert.
   * @return {@link EventResponseDTO} converted entity.
   */
  public EventResponseDTO toEventResponseDTO(Event event) {
    String name = event.getName();
    Place place = event.getPlace();
    String placeName = place.getName();
    String description = event.getDescription();
    LocalDateTime startDate = event.getStartDate();
    LocalDateTime endDate = event.getEndDate();

    List<EventGallery> gallery = event.getGalleries().stream().toList();
    byte[] image;
    if (gallery.isEmpty()) {
      image = null;
    } else {
      EventGallery eventGallery = gallery.getFirst();
      image = eventGallery.getImage();
    }

    return new EventResponseDTO(name, placeName, description, startDate, endDate, image);
  }
}
