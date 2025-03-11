package com.vibe_guide.services.impl;

import com.vibe_guide.converters.UserConverter;
import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.UserSortBy;
import com.vibe_guide.exceptions.MissingUsernameException;
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

    @Override
    public Page<UserPreviewResponseDTO> getPaginatedUsers(UserSortBy sortBy,
                                                          SortDirection sortDirection,
                                                          int page,
                                                          int size) {

        Pageable pageable = createPageable(sortBy, sortDirection, page, size);

        Page<User> userPage;

        userPage = userRepository.findAll(pageable);

        return userPage.map(userConverter::toUserPreviewResponseDTO);
    }

    @Override
    public UserPreviewResponseDTO getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userConverter.toUserPreviewResponseDTO(user);
    }

    @Override
    public UserPreviewResponseDTO getUserByUsername(String username, String sortBy, String direction) {
        if (username == null || username.isEmpty()) {
            throw new MissingUsernameException();
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        return userConverter.toUserPreviewResponseDTO(user);
    }


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
