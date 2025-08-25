package com.vibe_guide.services.impl;

import com.vibe_guide.converters.PlaceConverter;
import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.services.DailyOfferQueryService;
import com.vibe_guide.services.EventQueryService;
import com.vibe_guide.services.PlaceConverterWithAttributes;
import com.vibe_guide.services.PlaceTraitQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * This class is used to convert a {@link Place} into a DTO of type {@link PlaceResponseDTO}, with additional calls
 * to database for the purpose of retrieving additional data used in the response DTO.
 */
@AllArgsConstructor
@Component
public class PlaceConverterWithAttributesImpl implements PlaceConverterWithAttributes {
    private PlaceConverter placeConverter;
    private PlaceTraitQueryService placeTraitQueryService;
    private EventQueryService eventQueryService;
    private DailyOfferQueryService dailyOfferQueryService;

    /**
     * This class is used to convert a {@link Place} into a DTO of type {@link PlaceResponseDTO}, with additional calls
     * to database for the purpose of retrieving additional data used in the response DTO.
     *
     * @param place {@link Place} that is converted.
     * @return Response DTO of type {@link PlaceResponseDTO}.
     */
    @Override
    public PlaceResponseDTO getPlaceResponseDTO(Place place) {
        UUID placeId = place.getId();

        List<TraitResponseDTO> topTraits = placeTraitQueryService.getTopTraitsForPlace(placeId);
        List<EventResponseDTO> todaysEvents = eventQueryService.findTodaysEventsByPlaceId(placeId);
        List<DailyOfferResponseDTO> dailyOffers = dailyOfferQueryService.getDailyOffersByPlaceId(placeId);
        List<EventResponseDTO> monthlyEvents = eventQueryService.findUpcomingEventsByPlaceId(placeId);
        List<TraitCarouselResponseDTO> carouselTraits = placeTraitQueryService.getTraitsForDisplayInPlaceCarousel(placeId);

        return placeConverter.toPlaceResponseDTO(place, topTraits, todaysEvents, dailyOffers, monthlyEvents,
                carouselTraits);
    }
}