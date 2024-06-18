package com.shopnow.userservice.services;

import com.shopnow.userservice.common.exceptions.UserNotFoundException;
import com.shopnow.userservice.dto.request.LoginDTO;
import com.shopnow.userservice.dto.request.UserRequestDTO;
import com.shopnow.userservice.dto.response.UserResponseDTO;
import com.shopnow.userservice.models.User;
import com.shopnow.userservice.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public String registerUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setEmail(userRequestDTO.getEmail());
        user.setFullName(userRequestDTO.getFullName());
        userRepository.save(user);
        return "User registered successfully";
    }

    public String loginUser(LoginDTO loginDTO) {
        log.info("Before get user in Login");
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return user.getEmail();
        } else {
            throw new UserNotFoundException("Invalid credentials");
        }
    }

    public UserResponseDTO getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        return userResponseDTO;
    }
}
