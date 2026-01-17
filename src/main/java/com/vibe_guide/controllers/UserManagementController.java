package com.vibe_guide.controllers;

import com.vibe_guide.dtos.*;
import com.vibe_guide.enums.Role;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.UserSortBy;
import com.vibe_guide.services.UserManagementService;
import com.vibe_guide.services.UserQueryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final UserQueryService userQueryService;

    @GetMapping("/paginated")
    public ResponseEntity<Page<UserPreviewResponseDTO>> getPaginatedUsers(
            @RequestParam(required = false) Role role,
            @RequestParam(defaultValue = "DEFAULT") UserSortBy sortBy,
            @RequestParam(required = false) SortDirection sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<UserPreviewResponseDTO> users = userQueryService.getPaginatedUsers(role, sortBy, sortDirection, page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPreviewResponseDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userQueryService.getUserById(id));
    }

    @GetMapping("/by-username")
    public ResponseEntity<UserPreviewResponseDTO> getUserByUsername(
            @RequestParam String username,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction) {
        return ResponseEntity.ok(userQueryService.getUserByUsername(username, sortBy, direction));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(userManagementService.userRegister(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDTO loginRequestDTO,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        return ResponseEntity.ok(userManagementService.userLogin(loginRequestDTO, request, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful.");
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId,
                                             @RequestBody String password) {
        return ResponseEntity.ok(userManagementService.deleteUser(userId, password));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordRequestDTO changePasswordRequestDTO) {
        return ResponseEntity.ok(userManagementService.changePassword(changePasswordRequestDTO));
    }
}
