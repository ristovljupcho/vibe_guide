package com.vibe_guide.utils;

import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.enums.sorting.TraitSortBy;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class TraitTestData {
    private static final UUID TRAIT_ID = UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf841");
    private static final String TRAIT_NAME = "Trait 1";
    private static final Sort TRAIT_SORT = Sort.by(Sort.Direction.ASC, "name");

    public static final PageRequest TRAIT_PAGE_REQUEST =
            PageRequest.of(SharedTestData.PAGE, SharedTestData.SIZE, TRAIT_SORT);
    public static final TraitType TRAIT_TYPE = TraitType.MUSIC_TYPE;
    public static final TraitSortBy TRAIT_SORT_BY = TraitSortBy.NAME;

    public static Page<Trait> getPageTraits() {
        Trait trait1 = new Trait(
                TRAIT_ID,
                TRAIT_TYPE,
                TRAIT_NAME,
                new HashSet<>()
        );
        Trait trait2 = new Trait(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf842"),
                TRAIT_TYPE,
                TRAIT_NAME,
                new HashSet<>()
        );
        Trait trait3 = new Trait(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf843"),
                TRAIT_TYPE,
                TRAIT_NAME,
                new HashSet<>()
        );

        List<Trait> traits = List.of(trait1, trait2, trait3);

        return new PageImpl<>(traits);
    }

    public static Page<TraitResponseDTO> getPageTraitResponseDTOs() {
        TraitResponseDTO trait1 = new TraitResponseDTO(
                TRAIT_ID,
                TRAIT_TYPE,
                TRAIT_NAME
        );
        TraitResponseDTO trait2 = new TraitResponseDTO(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf842"),
                TRAIT_TYPE,
                TRAIT_NAME
        );
        TraitResponseDTO trait3 = new TraitResponseDTO(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf843"),
                TRAIT_TYPE,
                TRAIT_NAME
        );

        List<TraitResponseDTO> traits = List.of(trait1, trait2, trait3);

        return new PageImpl<>(traits);
    }
}