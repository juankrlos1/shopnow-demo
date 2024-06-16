package com.shopnow.userservice.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String username;
    private String email;
    private String fullName;
}
