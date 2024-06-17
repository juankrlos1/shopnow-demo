package com.shopnow.authservice.controllers;

import com.shopnow.authservice.dto.TokenDTO;
import com.shopnow.authservice.dto.UserDTO;
import com.shopnow.authservice.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO userDTO){
        log.info("Before request to login");
        return ResponseEntity.ok(authService.login(userDTO));
    }

    @PostMapping(path = "validate")
    public ResponseEntity<TokenDTO> validate(@RequestHeader String accessToken){
        return ResponseEntity.ok(this.authService.validateToken(
                TokenDTO
                        .builder()
                        .accessToken(accessToken)
                        .build()
        ));
    }
}
