package com.vibe_guide.services.impl;

import com.vibe_guide.converters.UserConverter;
import com.vibe_guide.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.User;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceAdminRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.PlaceAdminQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PlaceAdminQueryServiceImpl implements PlaceAdminQueryService {

    private final PlaceAdminRepository placeAdminRepository;
    private final PlaceRepository placeRepository;
    private final UserConverter userConverter;

    /**
     * Retrieves all admins/{@link User} for a {@link Place}.
     *
     * @param placeId ID of the {@link Place} that we retrieve admins for.
     * @return List of {@link PlaceAdminResponseDTO}.
     */
    @Override
    public List<PlaceAdminResponseDTO> getAllAdminsForPlace(UUID placeId) {
        if (!placeRepository.existsById(placeId)) {
            throw new PlaceNotFoundException(placeId);
        }

        List<User> users = placeAdminRepository.findAllByPlaceId(placeId);

        return users.stream().map(userConverter::toPlaceAdminResponseDTO).toList();
    }
}