package com.vibe_guide.eventgallery.services;

import com.vibe_guide.event.entities.Event;
import com.vibe_guide.event.repositories.EventRepository;
import com.vibe_guide.eventgallery.entities.EventGallery;
import com.vibe_guide.eventgallery.repositories.EventGalleryRepository;
import com.vibe_guide.exceptions.EventNotFoundException;
import com.vibe_guide.exceptions.ImageNotFoundException;
import com.vibe_guide.storage.ImageStorageService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class EventGalleryManagementServiceImpl implements EventGalleryManagementService {

  private final EventRepository eventRepository;
  private final EventGalleryRepository eventGalleryRepository;
  private final ImageStorageService imageStorageService;

  @Override
  @Transactional
  public void insertAll(UUID eventId, List<MultipartFile> images) {
    Event event =
        eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    List<EventGallery> eventGallery =
        images.stream()
            .filter(image -> image != null && !image.isEmpty())
            .map(
                image -> {
                  EventGallery gallery = new EventGallery();
                  gallery.setPhoto(imageStorageService.store(image, "events/" + eventId));
                  gallery.setEvent(event);
                  return gallery;
                })
            .toList();
    eventGalleryRepository.saveAll(eventGallery);
  }

  @Override
  public void deleteAll(UUID eventId) {
    eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    List<EventGallery> existingGalleries = eventGalleryRepository.findAllByEventId(eventId);
    existingGalleries.forEach(gallery -> imageStorageService.delete(gallery.getPhoto()));
    eventGalleryRepository.deleteAll(existingGalleries);
  }

  @Override
  public void deleteById(UUID imageId) {
    EventGallery gallery =
        eventGalleryRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(imageId));
    imageStorageService.delete(gallery.getPhoto());
    eventGalleryRepository.delete(gallery);
  }
}
