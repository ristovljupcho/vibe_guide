package com.vibe_guide.services;

import com.vibe_guide.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.dtos.PlaceTraitRequestDTO;

import java.util.UUID;

public interface PlaceTraitManagementService {
    String insertSingleTraitInPlace(UUID placeId, PlaceTraitRequestDTO placeTraitInsertRequestDTO);

    String batchInsertTraitsInPlace(UUID placeId, BatchInsertTraitsInPlace batchInsertTraitsInPlace);

    String updateTraitForPlace(UUID placeId, PlaceTraitRequestDTO placeTraitUpdateRequestDTO);

    String deleteSingleTraitInPlace(UUID placeId, UUID traitId);

    String batchDeleteTraitsInPlace(UUID placeId, BatchDeleteTraitsInPlace batchDeleteTraitsInPlace);
}
