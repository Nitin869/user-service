package com.socialapp.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;

    private String username;

    private String name;

    private String email;

    private String bio;
}
