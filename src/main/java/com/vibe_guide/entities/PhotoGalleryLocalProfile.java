package com.vibe_guide.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "photo_gallery_local_profile")
public class PhotoGalleryLocalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Byte photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_profile_id")
    @ToString.Exclude
    private LocalProfile localProfile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoGalleryLocalProfile that = (PhotoGalleryLocalProfile) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}