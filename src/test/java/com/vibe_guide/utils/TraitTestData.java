package com.vibe_guide.utils;

import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitInsertRequestDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.dtos.TraitUpdateRequestDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.views.TraitLikesSummary;
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
    private static final Sort TRAIT_SORT = Sort.by(Sort.Direction.ASC, "name");

    public static final UUID TRAIT_ID = UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf841");
    public static final UUID TRAIT_SECOND_ID = UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf842");
    public static final PageRequest TRAIT_PAGE_REQUEST =
            PageRequest.of(SharedTestData.PAGE, SharedTestData.SIZE, TRAIT_SORT);
    public static final String TRAIT_NAME = "Trait 1";
    public static final TraitType TRAIT_TYPE = TraitType.MUSIC_TYPE;
    public static final TraitSortBy TRAIT_SORT_BY = TraitSortBy.NAME;
    public static final String TRAIT_INSERT_MESSAGE =
            "Trait of type " + TRAIT_TYPE + " and with name " + TRAIT_NAME + " successfully inserted.";
    public static final String TRAIT_UPDATE_MESSAGE = "Trait with id " + TRAIT_ID + " successfully updated.";
    public static final String TRAIT_DELETE_MESSAGE = "Trait with id " + TRAIT_ID + " successfully deleted.";
    public static final UUID TRAIT_INVALID_ID = UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf849");

    public static Page<Trait> getPageTraits() {
        Trait trait1 = new Trait(
                TRAIT_ID,
                TRAIT_TYPE,
                TRAIT_NAME,
                new HashSet<>()
        );
        Trait trait2 = new Trait(
                TRAIT_SECOND_ID,
                TRAIT_TYPE,
                "Trait 2",
                new HashSet<>()
        );
        Trait trait3 = new Trait(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf843"),
                TRAIT_TYPE,
                "Trait 3",
                new HashSet<>()
        );

        List<Trait> traits = List.of(trait1, trait2, trait3);

        return new PageImpl<>(traits);
    }

    public static List<Trait> getTraits() {
        Trait trait1 = new Trait(
                TRAIT_ID,
                TRAIT_TYPE,
                TRAIT_NAME,
                new HashSet<>()
        );
        Trait trait2 = new Trait(
                TRAIT_SECOND_ID,
                TRAIT_TYPE,
                "Trait 2",
                new HashSet<>()
        );
        Trait trait3 = new Trait(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf843"),
                TRAIT_TYPE,
                "Trait 3",
                new HashSet<>()
        );

        return List.of(trait1, trait2, trait3);
    }

    public static Trait getTrait() {
        return getTraits().getFirst();
    }

    public static Page<TraitResponseDTO> getPageTraitResponseDTOs() {
        TraitResponseDTO trait1 = new TraitResponseDTO(
                TRAIT_ID,
                TRAIT_TYPE,
                TRAIT_NAME
        );
        TraitResponseDTO trait2 = new TraitResponseDTO(
                TRAIT_SECOND_ID,
                TRAIT_TYPE,
                "Trait 2"
        );
        TraitResponseDTO trait3 = new TraitResponseDTO(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf843"),
                TRAIT_TYPE,
                "Trait 3"
        );

        List<TraitResponseDTO> traits = List.of(trait1, trait2, trait3);

        return new PageImpl<>(traits);
    }

    public static TraitInsertRequestDTO getTraitInsertRequestDTO() {
        return new TraitInsertRequestDTO(
                TRAIT_TYPE,
                TRAIT_NAME
        );
    }

    public static TraitUpdateRequestDTO getTraitUpdateRequestDTO() {
        return new TraitUpdateRequestDTO(
                TRAIT_ID,
                TRAIT_TYPE,
                TRAIT_NAME
        );
    }

    public static List<TraitCarouselResponseDTO> getTraitCarouselResponseDTOs() {
        TraitCarouselResponseDTO trait1 = new TraitCarouselResponseDTO(TRAIT_NAME);
        TraitCarouselResponseDTO trait2 = new TraitCarouselResponseDTO("Trait 2");
        TraitCarouselResponseDTO trait3 = new TraitCarouselResponseDTO("Trait 3");

        return List.of(trait1, trait2, trait3);
    }

    public static List<TraitResponseDTO> getTraitResponseDTOs() {
        TraitResponseDTO trait1 = new TraitResponseDTO(
                TRAIT_ID,
                TRAIT_TYPE,
                TRAIT_NAME
        );
        TraitResponseDTO trait2 = new TraitResponseDTO(
                TRAIT_SECOND_ID,
                TRAIT_TYPE,
                "Trait 2"
        );
        TraitResponseDTO trait3 = new TraitResponseDTO(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf843"),
                TRAIT_TYPE,
                "Trait 2"
        );

        return List.of(trait1, trait2, trait3);
    }

    public static List<TraitLikesSummary> getTraitLikeSummaries() {
        TraitLikesSummary trait1 = new TraitLikesSummary();
        trait1.setId(TRAIT_ID);
        trait1.setTraitType(TRAIT_TYPE);
        trait1.setName(TRAIT_NAME);
        trait1.setTotalLikes(0);
        TraitLikesSummary trait2 = new TraitLikesSummary();
        trait2.setId(TRAIT_SECOND_ID);
        trait2.setTraitType(TRAIT_TYPE);
        trait2.setName("Trait 2");
        trait2.setTotalLikes(0);
        TraitLikesSummary trait3 = new TraitLikesSummary();
        trait3.setId(UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf843"));
        trait3.setTraitType(TRAIT_TYPE);
        trait3.setName("Trait 3");
        trait3.setTotalLikes(0);

        return List.of(trait1, trait2, trait3);
    }

    public static List<UUID> getTraitIds() {
        return List.of(TRAIT_ID);
    }
}