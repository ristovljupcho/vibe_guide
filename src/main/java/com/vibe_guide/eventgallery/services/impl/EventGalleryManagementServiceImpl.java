package com.vibe_guide.eventgallery.services.impl;

import com.vibe_guide.eventgallery.services.EventGalleryManagementService;
import com.vibe_guide.event.entities.Event;
import com.vibe_guide.event.repositories.EventRepository;
import com.vibe_guide.eventgallery.entities.EventGallery;
import com.vibe_guide.eventgallery.repositories.EventGalleryRepository;
import com.vibe_guide.exceptions.EventNotFoundException;
import com.vibe_guide.exceptions.ImageNotFoundException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class EventGalleryManagementServiceImpl implements EventGalleryManagementService {

  private final EventRepository eventRepository;
  private final EventGalleryRepository eventGalleryRepository;

  @Override
  @Transactional
  public void insertAll(UUID eventId, List<MultipartFile> images) {
    Event event =
        eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    List<EventGallery> eventGallery = new ArrayList<>();

    for (MultipartFile image : images) {
      if (image != null && !image.isEmpty()) {
        if (!Objects.requireNonNull(image.getContentType()).startsWith("image")) {
          throw new IllegalArgumentException("Uploaded file is not an image");
        }
        try {
          EventGallery gallery = new EventGallery();
          gallery.setPhoto(image.getBytes());
          gallery.setEvent(event);
          eventGallery.add(gallery);
        } catch (IOException e) {
          throw new RuntimeException("Failed to read image bytes", e);
        }
      }
    }
    eventGalleryRepository.saveAll(eventGallery);
  }

  @Override
  public void deleteAll(UUID eventId) {
    eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    List<EventGallery> existingGalleries = eventGalleryRepository.findAllByEventId(eventId);
    eventGalleryRepository.deleteAll(existingGalleries);
  }

  @Override
  public void deleteById(UUID imageId) {
    if (!eventGalleryRepository.existsById(imageId)) throw new ImageNotFoundException(imageId);

    eventGalleryRepository.deleteById(imageId);
  }
}

