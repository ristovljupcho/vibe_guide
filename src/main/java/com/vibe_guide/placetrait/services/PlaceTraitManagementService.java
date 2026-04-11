package com.vibe_guide.placetrait.services;

import com.vibe_guide.placetrait.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.placetrait.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.placetrait.dtos.PlaceTraitRequestDTO;
import java.util.UUID;

public interface PlaceTraitManagementService {
  String insertSingleTraitInPlace(PlaceTraitRequestDTO placeTraitInsertRequestDTO);

  String batchInsertTraitsInPlace(BatchInsertTraitsInPlace batchInsertTraitsInPlace);

  String updateTraitForPlace(PlaceTraitRequestDTO placeTraitUpdateRequestDTO);

  String deleteSingleTraitInPlace(UUID placeTraitId);

  String batchDeleteTraitsInPlace(BatchDeleteTraitsInPlace batchDeleteTraitsInPlace);
}
