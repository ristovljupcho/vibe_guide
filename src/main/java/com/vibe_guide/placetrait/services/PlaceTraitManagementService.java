package com.vibe_guide.placetrait.services;

import com.vibe_guide.placetrait.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.placetrait.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.placetrait.dtos.PlaceTraitRequestDTO;
import java.util.UUID;

public interface PlaceTraitManagementService {
  String insert(PlaceTraitRequestDTO placeTraitInsertRequestDTO);

  String insertAll(BatchInsertTraitsInPlace batchInsertTraitsInPlace);

  String update(PlaceTraitRequestDTO placeTraitUpdateRequestDTO);

  String delete(UUID placeTraitId);

  String deleteAll(BatchDeleteTraitsInPlace batchDeleteTraitsInPlace);
}
