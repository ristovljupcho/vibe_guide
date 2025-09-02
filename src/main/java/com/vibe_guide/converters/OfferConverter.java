package com.vibe_guide.converters;

import com.vibe_guide.dtos.OfferResponseDTO;
import com.vibe_guide.entities.Offer;
import com.vibe_guide.entities.Place;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <ul>
 *     <li>This class is responsible for providing conversion methods from {@link Offer} entity to Data Transfer
 *     Objects and vice versa.</li>
 * </ul>
 */
@Component
public class OfferConverter {
    /**
     * Converts a {@link Offer} entity to a {@link OfferResponseDTO} entity.
     *
     * @param offer {@link Offer} entity to convert.
     * @return {@link OfferResponseDTO} converted entity.
     */
    public OfferResponseDTO toOfferResponseDTO(Offer offer) {
        String name = offer.getName();
        Place place = offer.getPlace();
        String placeName = place.getName();
        String description = offer.getDescription();
        LocalDateTime startDate = offer.getStartDate();
        LocalDateTime endDate = offer.getEndDate();
        byte[] image = offer.getImage();

        return new OfferResponseDTO(name, placeName, description, startDate, endDate, image);
    }
}