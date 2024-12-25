package com.vibe_guide.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class LocalProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private String mapsUri;

    private String phoneNumber;

    private String address;

    private double rating = 0.0;

    private String menuLink;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trait_id")
    @ToString.Exclude
    private Trait trait;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "local_profile_admin", joinColumns = @JoinColumn(name = "local_profile_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalProfile that = (LocalProfile) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
