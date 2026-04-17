package com.vibe_guide.domain.placetrait.services;

import com.vibe_guide.domain.placetrait.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.domain.placetrait.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.domain.placetrait.dtos.PlaceTraitRequestDTO;
import java.util.UUID;

public interface PlaceTraitManagementService {
  String insert(PlaceTraitRequestDTO placeTraitInsertRequestDTO);

  String insertAll(BatchInsertTraitsInPlace batchInsertTraitsInPlace);

  String update(PlaceTraitRequestDTO placeTraitUpdateRequestDTO);

  String delete(UUID placeTraitId);

  String deleteAll(BatchDeleteTraitsInPlace batchDeleteTraitsInPlace);
}

