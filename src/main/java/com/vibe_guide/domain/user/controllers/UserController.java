package com.vibe_guide.domain.user.controllers;

import com.vibe_guide.domain.user.dtos.UserPreviewResponseDTO;
import com.vibe_guide.domain.user.dtos.UserUpsertRequestDTO;
import com.vibe_guide.domain.user.services.UserQueryService;
import com.vibe_guide.domain.user.services.UserUpsertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserQueryService userQueryService;
  private final UserUpsertService userUpsertService;

  @GetMapping("/{userId}")
  public ResponseEntity<UserPreviewResponseDTO> getById(@PathVariable String userId) {
    return ResponseEntity.ok(userQueryService.getById(userId));
  }

  /** Creates or updates the local user record from Clerk identity data. */
  @PostMapping
  public ResponseEntity<UserPreviewResponseDTO> upsert(
      @RequestBody @Valid UserUpsertRequestDTO dto) {
    return ResponseEntity.ok(userUpsertService.upsert(dto));
  }
}
