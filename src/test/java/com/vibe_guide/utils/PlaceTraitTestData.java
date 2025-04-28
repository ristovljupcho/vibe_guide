package com.vibe_guide.utils;

import com.vibe_guide.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.entities.PlaceTrait;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class PlaceTraitTestData {
    public List<PlaceTrait> getPlaceTraits() {
        PlaceTrait placeTrait = new PlaceTrait(
                PlaceTestData.getPlace(),
                TraitTestData.getTrait(),
                "Additional information.",
                0,
                false
        );

        return List.of(placeTrait);
    }

    public PlaceTrait getPlaceTrait() {
        return getPlaceTraits().getFirst();
    }

    public List<PlaceTraitRequestDTO> getPlaceTraitRequestDTOs() {
        PlaceTraitRequestDTO placeTrait1 = new PlaceTraitRequestDTO(
                TraitTestData.TRAIT_ID,
                "Additional information.",
                true
        );

        return List.of(placeTrait1);
    }

    public PlaceTraitRequestDTO getPlaceTraitRequestDTO() {
        return getPlaceTraitRequestDTOs().getFirst();
    }

    public static BatchInsertTraitsInPlace getBatchInsertTraitsInPlace() {
        return new BatchInsertTraitsInPlace(
                getPlaceTraitRequestDTOs()
        );
    }

    public List<PlaceTraitRequestDTO> getInvalidPlaceTraitRequestDTOs() {
        PlaceTraitRequestDTO placeTrait = new PlaceTraitRequestDTO(
                TraitTestData.TRAIT_INVALID_ID,
                "Additional information.",
                true
        );

        return List.of(placeTrait);
    }

    public static BatchInsertTraitsInPlace getInvalidBatchInsertTraitsInPlace() {
        return new BatchInsertTraitsInPlace(
                getInvalidPlaceTraitRequestDTOs()
        );
    }

    private static List<PlaceTraitRequestDTO> getNewPlaceTraitRequestDTOs() {
        PlaceTraitRequestDTO placeTrait = new PlaceTraitRequestDTO(
                TraitTestData.TRAIT_SECOND_ID,
                "Additional information.",
                true
        );

        return List.of(placeTrait);
    }

    public static BatchInsertTraitsInPlace getBatchSuccessfulInsertTraitsInPlace() {
        return new BatchInsertTraitsInPlace(
                getNewPlaceTraitRequestDTOs()
        );
    }

    public static List<UUID> getPlaceTraitIds() {
        return getPlaceTraits().stream().map(pt -> pt.getTrait().getId()).toList();
    }

    public static BatchDeleteTraitsInPlace getBatchDeleteTraitsInPlaceDTO() {
        return new BatchDeleteTraitsInPlace(
                List.of(TraitTestData.TRAIT_ID)
        );
    }

    public static BatchDeleteTraitsInPlace getInvalidBatchDeleteTraitsInPlaceDTO() {
        return new BatchDeleteTraitsInPlace(
                List.of(TraitTestData.TRAIT_SECOND_ID)
        );
    }
}
