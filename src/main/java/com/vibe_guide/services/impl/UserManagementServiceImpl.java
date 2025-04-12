package com.vibe_guide.services.impl;

import com.vibe_guide.config.jwt.JWTService;
import com.vibe_guide.dtos.UserChangePasswordRequestDTO;
import com.vibe_guide.dtos.UserLoginRequestDTO;
import com.vibe_guide.dtos.UserRegisterRequestDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.enums.Role;
import com.vibe_guide.exceptions.UserInvalidCredentialsException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.services.UserManagementService;
import com.vibe_guide.utils.UserResponseMessages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Transactional
    @Override
    public String userRegister(UserRegisterRequestDTO registerRequestDTO) {

        User user = new User();
        user.setUsername(registerRequestDTO.username());
        user.setEmail(registerRequestDTO.email());
        user.setName(registerRequestDTO.name());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return UserResponseMessages.USER_REGISTER_MESSAGE;
    }

    @Transactional
    @Override
    public String userLogin(UserLoginRequestDTO userLoginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        User user = userRepository.findByUsername(userLoginRequestDTO.username())
                .orElseThrow(() -> new UserNotFoundException(userLoginRequestDTO.username()));

        if (!passwordEncoder.matches(userLoginRequestDTO.password(), user.getPassword())) {
            throw new UserInvalidCredentialsException(userLoginRequestDTO.username());
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(user.getUsername());
        response.setHeader("Authorization", "Bearer " + token);
        request.getSession().setAttribute("user", user);
        return UserResponseMessages.USER_LOGIN_MESSAGE;
    }

    @Transactional
    @Override
    public String userLogout(UUID userId) {
        SecurityContextHolder.clearContext();
        return UserResponseMessages.USER_LOGOUT_MESSAGE;
    }

    @Transactional
    @Override
    public String changePassword(UserChangePasswordRequestDTO changePasswordRequestDTO) {
        User user = userRepository.findById(changePasswordRequestDTO.userId())
                .orElseThrow(() -> new UserNotFoundException(changePasswordRequestDTO.userId()));

        validatePasswordChange(user, changePasswordRequestDTO);
        user.setPassword(passwordEncoder.encode(changePasswordRequestDTO.newPassword()));
        userRepository.save(user);
        return UserResponseMessages.USER_CHANGE_PASSWORD_MESSAGE;
    }

    @Transactional
    @Override
    public String deleteUser(UUID userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
        return UserResponseMessages.USER_DELETE_MESSAGE;
    }

    private void validatePasswordChange(User user, UserChangePasswordRequestDTO changePasswordRequestDTO) {
        if (!passwordEncoder.matches(changePasswordRequestDTO.currentPassword(), user.getPassword())) {
            throw new UserInvalidCredentialsException(user.getUsername());
        }
        if (!changePasswordRequestDTO.newPassword().equals(changePasswordRequestDTO.confirmNewPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }
}

