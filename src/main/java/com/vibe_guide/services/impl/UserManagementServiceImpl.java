package com.vibe_guide.services.impl;

//import com.vibe_guide.config.jwt.JwtBlacklistService;
import com.vibe_guide.dtos.UserChangePasswordRequestDTO;
import com.vibe_guide.dtos.UserLoginRequestDTO;
import com.vibe_guide.dtos.UserRegisterRequestDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.enums.Role;
import com.vibe_guide.exceptions.UserInvalidCredentialsException;
import com.vibe_guide.exceptions.UserEmailAlreadyExistsException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.exceptions.UsernameAlreadyTakenException;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.services.UserManagementService;
import com.vibe_guide.utils.UserResponseMessages;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final JwtBlacklistService jwtBlacklistService;

    @Transactional
    @Override
    public String userRegister(UserRegisterRequestDTO registerRequestDTO) {
        checkIfUserExistsByUsernameAndEmail(registerRequestDTO.username(), registerRequestDTO.email());

        User user = new User();
        user.setUsername(registerRequestDTO.username());
        user.setEmail(registerRequestDTO.email());
        user.setName(registerRequestDTO.name());
        user.setRole(Role.USER);
        String password = passwordEncoder.encode(registerRequestDTO.password());
        user.setPassword(password);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return UserResponseMessages.USER_REGISTER_MESSAGE;
    }

    @Override
    public String userLogin(UserLoginRequestDTO userLoginRequestDTO) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginRequestDTO.username());
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException(userLoginRequestDTO.username());
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(userLoginRequestDTO.password(), user.getPassword())) {//private method
            throw new UserInvalidCredentialsException(userLoginRequestDTO.username());
        }
        return UserResponseMessages.USER_LOGIN_MESSAGE;
    }

    @Override
    public String userLogout(UUID userId) {
        return UserResponseMessages.USER_LOGOUT_MESSAGE;
    }

    @Override
    public String changePassword(UserChangePasswordRequestDTO changePasswordRequestDTO) {
        return userRepository.findById(changePasswordRequestDTO.userId())
                .map(user -> {
                    String username = user.getUsername();

                    if (!passwordEncoder.matches(changePasswordRequestDTO.currentPassword(), user.getPassword())) {
                        throw new UserInvalidCredentialsException(username);
                    }

                    if (!changePasswordRequestDTO.newPassword().equals(changePasswordRequestDTO.confirmNewPassword())) {
                        throw new IllegalArgumentException("Passwords do not match");
                    }

                    if (changePasswordRequestDTO.newPassword().length() < 8) {
                        throw new IllegalArgumentException("New password must be at least 8 characters long for user:" +
                                " " + username);//remove
                    }

                    user.setPassword(passwordEncoder.encode(changePasswordRequestDTO.newPassword()));
                    userRepository.save(user);

                    return UserResponseMessages.USER_CHANGE_PASSWORD_MESSAGE;
                })
                .orElseThrow(() -> new UserNotFoundException(changePasswordRequestDTO.userId()));
    }


    @Override
    public String deleteUser(UUID userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
        return UserResponseMessages.USER_DELETE_MESSAGE;
    }


    private void checkIfUserExistsByUsernameAndEmail(String username , String email) {
        Optional<User> userOptionalUsername = userRepository.findByUsername(username);
        Optional<User> userOptionalEmail = userRepository.findByEmail(email);
        if(userOptionalUsername.isPresent()){
            throw new UsernameAlreadyTakenException(username);
        }
        if(userOptionalEmail.isPresent()){
            throw new UserEmailAlreadyExistsException(email);
        }
    }
}
