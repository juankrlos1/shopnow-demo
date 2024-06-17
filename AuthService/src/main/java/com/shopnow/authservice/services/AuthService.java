package com.shopnow.authservice.services;

import com.shopnow.authservice.dto.TokenDTO;
import com.shopnow.authservice.dto.UserDTO;

public interface AuthService {

    TokenDTO login(UserDTO userDto);
    TokenDTO validateToken(TokenDTO tokenDTO);

}
