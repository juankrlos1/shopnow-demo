package com.shopnow.userservice.controllers;

import com.shopnow.userservice.dto.request.LoginDTO;
import com.shopnow.userservice.dto.request.UserRequestDTO;
import com.shopnow.userservice.dto.response.UserResponseDTO;
import com.shopnow.userservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.registerUser(userRequestDTO);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginDTO loginDTO) {
        log.info("Get user in controller");
        return userService.loginUser(loginDTO);
    }

    @PostMapping("/validate")
    public String validateToken(@RequestBody LoginDTO loginDTO) {
        log.info("Get user in controller for validate token");
        return userService.loginUser(loginDTO);
    }

    @GetMapping("/profile")
    public UserResponseDTO getProfile(@RequestParam String username) {
        log.info("Get profile for user {}", username);
        return userService.getProfile(username);
    }
}
