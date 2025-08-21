package com.vibe_guide.converters;

import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import com.vibe_guide.projections.PlacePreviewProjection;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * <ul>
 *     <li>This class is responsible for providing conversion methods from {@link Place} entity to Data Transfer
 *     Objects and vice versa.</li>
 * </ul>
 */
@Component
public class PlaceConverter {

    /**
     * Converts a {@link Place} entity with additional attributes:
     * <ul>
     *     <li>List of {@link TraitResponseDTO} as topTraits</li>
     *     <li>List of {@link EventResponseDTO} as todaysEvents</li>
     *     <li>List of {@link DailyOfferResponseDTO} as dailyOffers</li>
     *     <li>List of {@link EventResponseDTO} as dailyEvents</li>
     *     <li>List of {@link EventResponseDTO} as monthlyEvents</li>
     *     <li>List of {@link TraitCarouselResponseDTO} as carouselTraits</li>
     * </ul>
     * to a {@link PlaceResponseDTO} entity.
     *
     * @param place          {@link Place} entity to convert.
     * @param topTraits      List of {@link TraitResponseDTO}.
     * @param todaysEvents   List of {@link EventResponseDTO}.
     * @param dailyOffers    List of {@link DailyOfferResponseDTO}.
     * @param monthlyEvents  List of {@link EventResponseDTO}.
     * @param carouselTraits List of {@link TraitCarouselResponseDTO}.
     * @return {@link PlaceResponseDTO} converted entity.
     */
    public PlaceResponseDTO toPlaceResponseDTO(Place place,
                                               List<TraitResponseDTO> topTraits,
                                               List<EventResponseDTO> todaysEvents,
                                               List<DailyOfferResponseDTO> dailyOffers,
                                               List<EventResponseDTO> monthlyEvents,
                                               List<TraitCarouselResponseDTO> carouselTraits) {
        UUID placeId = place.getId();
        String name = place.getName();
        String description = place.getDescription();
        String mapsUri = place.getMapsUri();
        String phoneNumber = place.getPhoneNumber();
        String address = place.getAddress();
        double rating = place.getRating();
        String menuLink = place.getMenuLink();
        PrimaryType primaryType = place.getPrimaryType();
        PriceLevel priceLevel = place.getPriceLevel();

        List<String> gallery = place.getGallery().stream()
                .map(g -> Base64.getEncoder().encodeToString(g.getPhoto())).toList();

        return new PlaceResponseDTO(
                placeId,
                name,
                description,
                mapsUri,
                phoneNumber,
                address,
                rating,
                menuLink,
                primaryType,
                priceLevel,
                gallery,
                topTraits,
                todaysEvents,
                dailyOffers,
                monthlyEvents,
                carouselTraits
        );
    }


    /**
     * Converts projection {@link PlacePreviewProjection} used for retrieving data from repository to usable DTO
     * {@link PlacePreviewResponseDTO}.
     *
     * @param placePreviewProjection entity to convert.
     * @return {@link PlacePreviewResponseDTO} converted entity.
     */
    public PlacePreviewResponseDTO toPlacePreviewResponseDTO(PlacePreviewProjection placePreviewProjection) {
        return new PlacePreviewResponseDTO(
                placePreviewProjection.getPlaceId(),
                placePreviewProjection.getPlaceName(),
                placePreviewProjection.getDescription(),
                placePreviewProjection.getAddress(),
                placePreviewProjection.getRating(),
                placePreviewProjection.getPrimaryType(),
                placePreviewProjection.getTopTraits()
        );
    }
}
