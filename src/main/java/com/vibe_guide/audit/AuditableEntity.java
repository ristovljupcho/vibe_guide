package com.vibe_guide.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity {

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "created_by")
  private UUID createdBy;

  @Column(name = "updated_by")
  private UUID updatedBy;

  @PrePersist
  protected void prePersist() {
    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }

    // TODO: Populate createdBy from the authenticated user context once request-level auditing is
    // implemented.
  }

  @PreUpdate
  protected void preUpdate() {
    updatedAt = LocalDateTime.now();

    // TODO: Populate updatedBy from the authenticated user context once request-level auditing is
    // implemented.
  }
}
