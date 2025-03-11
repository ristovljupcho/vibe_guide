package com.vibe_guide.services.impl;


import com.vibe_guide.converters.UserConverter;
import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.enums.Role;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.UserSortBy;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.services.UserQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    /**
     * Retrieves {@link User} objects using pagination. Sorting by USERNAME or DEFAULT (UUID), in ASC or DESC order.
     *
     * @param sortDirection used for sorting direction, default sort direction is <b><i>ASC</i></b> from enum
     *                      {@link SortDirection}.
     * @param sortBy        used for sorting, default review sort criteria is <b><i>TYPE</i></b> from enum
     *                      {@link UserSortBy}.
     * @param page          page number.
     * @param size          page size.
     * @return A {@link Page} containing {@link UserPreviewResponseDTO} objects.
     */
    @Override
    public Page<UserPreviewResponseDTO> getPaginatedUsers(Role role,
                                                          UserSortBy sortBy,
                                                          SortDirection sortDirection,
                                                          int page,
                                                          int size) {

        Pageable pageable = createPageable(sortBy, sortDirection, page, size);

        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(userConverter::toUserPreviewResponseDTO);
    }
    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId The UUID of the user.
     * @return The user mapped to {@link UserPreviewResponseDTO}.
     */
    @Override
    public UserPreviewResponseDTO getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userConverter.toUserPreviewResponseDTO(user);
    }
    /**
     * Retrieves a user by their username.
     *
     * @param username  The username of the user.
     * @param sortBy    The field by which results should be sorted (not used in this method but kept for future
     *                  extensibility).
     * @param direction The sorting direction (not used in this method but kept for future extensibility).
     * @return The user mapped to {@link UserPreviewResponseDTO}.
     **/
    @Override
    public UserPreviewResponseDTO getUserByUsername(String username, String sortBy, String direction) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return userConverter.toUserPreviewResponseDTO(user);
    }
    /**
     * Creates a {@link Pageable} object for pagination and sorting.
     *
     * @param sortBy        sortBy used for sorting, default review sort criteria is <b><i>TYPE</i></b> from enum
     *                      {@link UserSortBy}.
     * @param sortDirection sortDirection used for sorting direction, default sort direction is <b><i>ASC</i></b>
     *                      from enum
     *                      {@link SortDirection}.
     * @param page          The page number (zero-based) for pagination.
     * @param size          The number of elements per page.
     * @return A {@link Pageable} instance configured with sorting and pagination settings.
     */
    private Pageable createPageable(UserSortBy sortBy, SortDirection sortDirection, int page, int size) {
        String sortField = switch (sortBy) {
            case DEFAULT -> "id";
            case USERNAME -> "username";
        };

        Sort sort =
                Sort.by(sortDirection == SortDirection.DESC ? Sort.Order.desc(sortField) : Sort.Order.asc(sortField));
        return PageRequest.of(page, size, sort);
    }
}
