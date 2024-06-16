package com.shopnow.userservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse<T> {
    private String status;
    private String message;
    private T data;
}