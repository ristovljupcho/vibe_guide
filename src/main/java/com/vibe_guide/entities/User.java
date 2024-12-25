package com.vibe_guide.entities;

import com.vibe_guide.enums.Role;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}