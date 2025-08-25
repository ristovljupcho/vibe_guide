package com.vibe_guide.services.impl;

import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.EventGallery;
import com.vibe_guide.exceptions.EventNotFoundException;
import com.vibe_guide.exceptions.ImageNotFoundException;
import com.vibe_guide.repositories.EventGalleryRepository;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.services.EventGalleryManagementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Service
public class EventGalleryManagementServiceImpl implements EventGalleryManagementService {

    private final EventRepository eventRepository;
    private final EventGalleryRepository eventGalleryRepository;

    /**
     * Inserts {@link EventGallery} objects with provided<b><i>UUID eventId</i></b>.
     *
     * @param eventId UUID of the {@link Event} object, for which the images need to be inserted.
     * @param images  list of MultipartFile images
     */
    @Override
    @Transactional
    public void addImagesToEvent(UUID eventId, List<MultipartFile> images) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        List<EventGallery> eventGallery
                = new ArrayList<>();

        for (MultipartFile image : images) {
            if (image != null && !image.isEmpty()) {
                if (!Objects.requireNonNull(image.getContentType()).startsWith("image")) {
                    throw new IllegalArgumentException("Uploaded file is not an image");
                }
                try {
                    EventGallery gallery = new EventGallery();
                    gallery.setEvent(event);
                    gallery.setPhoto(image.getBytes());
                    eventGallery.add(gallery);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to read image bytes", e);
                }
            }
        }
        eventGalleryRepository.saveAll(eventGallery);
    }

    /**
     * Deletes all {@link EventGallery} objects with provided <b><i>UUID eventId</i></b>.
     *
     * @param eventId UUID of the {@link Event} objects, for which images need to be deleted.
     */
    @Override
    public void deleteAllImagesFromEvent(UUID eventId) {
        eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        List<EventGallery> existingGalleries = eventGalleryRepository.findAllByEventId(eventId);
        eventGalleryRepository.deleteAll(existingGalleries);
    }

    /**
     * Deletes a {@link EventGallery} object with provided <b><i>UUID imageId</i></b>.
     *
     * @param imageId UUID of the {@link EventGallery} object that needs to be deleted.
     */
    @Override
    public void deleteImageFromEvent(UUID imageId) {
        if (!eventGalleryRepository.existsById(imageId)) {
            throw new ImageNotFoundException(imageId);
        }
        eventGalleryRepository.deleteById(imageId);
    }
}