package com.socialapp.userservice.dto;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
