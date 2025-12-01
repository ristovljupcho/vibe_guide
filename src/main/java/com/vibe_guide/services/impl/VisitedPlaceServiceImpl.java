package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.VisitedPlace;
import com.vibe_guide.entities.composite_keys.VisitedPlaceId;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.repositories.VisitedPlaceRepository;
import com.vibe_guide.services.VisitedPlaceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VisitedPlaceServiceImpl implements VisitedPlaceService {

    private final VisitedPlaceRepository visitedPlaceRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    /**
     * Retrieves all places visited by a given user.
     *
     * @param userId the ID of the user
     * @return a list of {@link VisitedPlaceResponseDTO} containing visited place data
     * @throws UserNotFoundException if the user does not exist
     */
    @Override
    public List<VisitedPlaceResponseDTO> getVisitedPlacesByUserId(UUID userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        return visitedPlaceRepository.findVisitedPlacesByUser(userId);
    }

    /**
     * Toggles the visited state of a place for a user.
     *
     * <p>If the place is already marked as visited, it is removed from the visited list.
     * If it is not marked as visited, a new entry is created.</p>
     *
     * @param userId  the ID of the user performing the action
     * @param placeId the ID of the place being marked/unmarked as visited
     * @return a confirmation message indicating the result of the operation
     * @throws UserNotFoundException  if the user does not exist
     * @throws PlaceNotFoundException if the place does not exist
     */
    @Override
    @Transactional
    public String manageVisitedPlace(UUID userId, UUID placeId) {

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException(placeId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        VisitedPlaceId visitedPlaceId = new VisitedPlaceId(userId, placeId);

        Optional<VisitedPlace> existing =
                visitedPlaceRepository.findById(visitedPlaceId);

        if (existing.isPresent()) {
            visitedPlaceRepository.delete(existing.get());
            return "Place removed from visited list.";
        }

        VisitedPlace visitedPlace = new VisitedPlace(
                user,
                place,
                LocalDateTime.now(),
                null
        );

        visitedPlaceRepository.save(visitedPlace);

        return "Place marked as visited.";
    }
}
