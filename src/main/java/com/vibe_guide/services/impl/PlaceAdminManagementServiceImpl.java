package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.PlaceAdminRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.PlaceAdmin;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.composite_keys.PlaceAdminId;
import com.vibe_guide.exceptions.AdminForPlaceAlreadyExistsException;
import com.vibe_guide.exceptions.AdminForPlaceNotFound;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.repositories.PlaceAdminRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.services.PlaceAdminManagementService;
import com.vibe_guide.utils.PlaceAdminResponseMessages;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class PlaceAdminManagementServiceImpl implements PlaceAdminManagementService {

    private final PlaceAdminRepository placeAdminRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    /**
     * Inserts {@link User} with role admin for a {@link Place} with provided ID.
     *
     * @param placeId              ID of {@link Place}.
     * @param placeAdminRequestDTO DTO of type {@link PlaceAdminRequestDTO} containing ID of the {@link User}.
     * @return Response message of type {@link PlaceAdminResponseMessages}.
     */
    @Transactional
    @Override
    public String addPlaceAdmin(UUID placeId, PlaceAdminRequestDTO placeAdminRequestDTO) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

        UUID userId = placeAdminRequestDTO.userId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        PlaceAdminId placeAdminId = new PlaceAdminId(placeId, userId);
        if (placeAdminRepository.existsById(placeAdminId)) {
            throw new AdminForPlaceAlreadyExistsException(placeId, userId);
        }

        PlaceAdmin placeAdmin = new PlaceAdmin();
        placeAdmin.setPlace(place);
        placeAdmin.setUser(user);

        placeAdminRepository.save(placeAdmin);

        return String.format(PlaceAdminResponseMessages.PLACE_ADMIN_INSERT_MESSAGE, userId, placeId);
    }

    /**
     * Deletes {@link User} for a {@link Place} with provided ID.
     *
     * @param placeId ID of {@link Place}.
     * @param userId  ID of {@link User}.
     * @return Response message of type {@link PlaceAdminResponseMessages}.
     */
    public String deletePlaceAdmin(UUID placeId, UUID userId) {
        PlaceAdminId placeAdminId = new PlaceAdminId(placeId, userId);
        if (!placeAdminRepository.existsById(placeAdminId)) {
            throw new AdminForPlaceNotFound(placeId, userId);
        }

        placeAdminRepository.deleteById(placeAdminId);

        return String.format(PlaceAdminResponseMessages.PLACE_ADMIN_DELETE_MESSAGE, userId, placeId);
    }
}