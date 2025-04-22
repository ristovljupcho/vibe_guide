package com.vibe_guide.utils;

import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.PlaceAdmin;
import com.vibe_guide.entities.PlaceTrait;
import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@UtilityClass
public class PlaceTestData {
    private static final String PLACE_DESCRIPTION = "Place Description";
    private static final String PLACE_MAPS_URI = "Place Maps URI";
    private static final String PLACE_PHONE_NUMBER = "Phone Number";
    private static final String PLACE_ADDRESS = "Place Address";
    private static final double PLACE_RATING = 1.0;
    private static final String PLACE_MENU_LINK = "Place Menu Link";
    private static final PrimaryType PLACE_PRIMARY_TYPE = PrimaryType.BAR;
    private static final PriceLevel PLACE_PRICE_LEVEL = PriceLevel.EXPENSIVE;
    private static final Set<PlaceAdmin> PLACE_ADMIN_SET = new HashSet<>();
    private static final Set<PlaceTrait> PLACE_TRAIT_SET = new HashSet<>();

    public static final UUID PLACE_ID = UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf811");
    public static final String PLACE_NAME = "Place Name 1";

    public List<Place> getPlaces() {
        Place place1 = new Place(
                PLACE_ID,
                PLACE_NAME,
                PLACE_DESCRIPTION,
                PLACE_MAPS_URI,
                PLACE_PHONE_NUMBER,
                PLACE_ADDRESS,
                PLACE_RATING,
                PLACE_MENU_LINK,
                PLACE_PRIMARY_TYPE,
                PLACE_PRICE_LEVEL,
                PLACE_ADMIN_SET,
                PLACE_TRAIT_SET
        );
        Place place2 = new Place(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf812"),
                "Place Name 2",
                PLACE_DESCRIPTION,
                PLACE_MAPS_URI,
                PLACE_PHONE_NUMBER,
                PLACE_ADDRESS,
                PLACE_RATING,
                PLACE_MENU_LINK,
                PLACE_PRIMARY_TYPE,
                PLACE_PRICE_LEVEL,
                PLACE_ADMIN_SET,
                PLACE_TRAIT_SET
        );
        Place place3 = new Place(
                UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf813"),
                "Place Name 3",
                PLACE_DESCRIPTION,
                PLACE_MAPS_URI,
                PLACE_PHONE_NUMBER,
                PLACE_ADDRESS,
                PLACE_RATING,
                PLACE_MENU_LINK,
                PLACE_PRIMARY_TYPE,
                PLACE_PRICE_LEVEL,
                PLACE_ADMIN_SET,
                PLACE_TRAIT_SET
        );

        return List.of(place1, place2, place3);
    }

    public Place getPlace() {
        return getPlaces().getFirst();
    }
}
