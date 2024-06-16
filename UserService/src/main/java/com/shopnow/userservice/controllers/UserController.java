package com.shopnow.userservice.controllers;

import com.shopnow.userservice.dto.request.LoginDTO;
import com.shopnow.userservice.dto.request.UserRequestDTO;
import com.shopnow.userservice.dto.response.UserResponseDTO;
import com.shopnow.userservice.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
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
        return userService.loginUser(loginDTO);
    }

    @GetMapping("/profile")
    public UserResponseDTO getProfile(@RequestParam String username) {
        return userService.getProfile(username);
    }
}
