package com.vibe_guide.entities;

import com.vibe_guide.entities.composite_keys.LocalProfileTraitId;
import com.vibe_guide.enums.PriorityLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@IdClass(LocalProfileTraitId.class)
public class LocalProfileTrait {
    @Id
    @ManyToOne
    @JoinColumn(name = "local_profile_id")
    @ToString.Exclude
    private LocalProfile localProfile;

    @Id
    @ManyToOne
    @JoinColumn(name = "trait_id")
    @ToString.Exclude
    private Trait trait;

    private String additionalInformation;

    private Integer likeCounter;

    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalProfileTrait that = (LocalProfileTrait) o;
        return Objects.equals(localProfile, that.localProfile) &&
                Objects.equals(trait, that.trait);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localProfile, trait);
    }
}