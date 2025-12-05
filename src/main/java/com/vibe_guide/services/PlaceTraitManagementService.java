package com.vibe_guide.services;

import com.vibe_guide.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.dtos.PlaceTraitRequestDTO;

import java.util.UUID;

public interface PlaceTraitManagementService {
    String insertSingleTraitInPlace(PlaceTraitRequestDTO placeTraitInsertRequestDTO);

    String batchInsertTraitsInPlace(BatchInsertTraitsInPlace batchInsertTraitsInPlace);

    String updateTraitForPlace(PlaceTraitRequestDTO placeTraitUpdateRequestDTO);

    String deleteSingleTraitInPlace(UUID placeTraitId);

    String batchDeleteTraitsInPlace(BatchDeleteTraitsInPlace batchDeleteTraitsInPlace);
}
