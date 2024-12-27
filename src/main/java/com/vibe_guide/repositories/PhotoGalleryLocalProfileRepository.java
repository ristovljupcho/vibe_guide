package com.vibe_guide.repositories;

import com.vibe_guide.entities.PhotoGalleryLocalProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoGalleryLocalProfileRepository extends JpaRepository<PhotoGalleryLocalProfile, UUID> {
}
