package com.vibe_guide.placetrait.entities;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceTraitId implements Serializable {
  private UUID trait;
  private UUID place;
}
