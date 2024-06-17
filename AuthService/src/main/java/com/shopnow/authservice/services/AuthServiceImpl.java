package com.shopnow.authservice.services;

import com.shopnow.authservice.common.helpers.JwtHelper;
import com.shopnow.authservice.dto.TokenDTO;
import com.shopnow.authservice.dto.UserDTO;
import com.shopnow.authservice.models.User;
import com.shopnow.authservice.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    private static final String USER_EXCEPTION_MSG = "Invalid username or password";

    @Override
    public TokenDTO login(UserDTO userDto) {
        final var user = this.userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG)
                );
        log.info("User logged in: {}", user);
        this.validPassword(userDto, user);

        return TokenDTO
                .builder()
                .accessToken(this.jwtHelper.createToken(user.getUsername()))
                .build();
    }

    @Override
    public TokenDTO validateToken(TokenDTO token) {
        if(!this.jwtHelper.validateToken(token.getAccessToken())){
            return TokenDTO
                    .builder()
                    .accessToken(token.getAccessToken())
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG);
    }

    private void validPassword(UserDTO userDto, User user) {
        if (!this.passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG);
        }
    }
}
