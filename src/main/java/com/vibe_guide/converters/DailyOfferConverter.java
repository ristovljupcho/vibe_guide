package com.vibe_guide.converters;

import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.dtos.TraitPreviewResponseDTO;
import com.vibe_guide.entities.DailyOffer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
/**
 * <ul>
 *     <li>This class is responsible for providing conversion methods from {@link DailyOffer} entity to Data Transfer
 *     Objects and vice versa.</li>
 * </ul>
 */
@Component
public class DailyOfferConverter {
    /**
     * Converts a {@link DailyOffer} entity to a {@link DailyOfferResponseDTO} entity.
     * @param dailyOffer {@link DailyOffer} entity to convert.
     * @return {@link DailyOfferResponseDTO} converted entity.
     */
    public DailyOfferResponseDTO toDailyOfferResponseDTO(DailyOffer dailyOffer) {
        String name = dailyOffer.getName();
        LocalDateTime startDate = dailyOffer.getStartDate();
        LocalDateTime endDate = dailyOffer.getEndDate();
        String description = dailyOffer.getDescription();
        byte[] image = dailyOffer.getImage();

        return new DailyOfferResponseDTO(name, startDate, endDate, description, image);
    }
}