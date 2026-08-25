package com.socialapp.userservice.dto;

import lombok.Data;

@Data
public class UpdateUser {

    private String name;
    private String username;
    private String email;
    private String password;
    private String bio;

}
