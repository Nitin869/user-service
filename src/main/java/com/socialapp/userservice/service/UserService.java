package com.socialapp.userservice.service;

import com.socialapp.userservice.dto.LoginRequest;
import com.socialapp.userservice.dto.LoginResponse;
import com.socialapp.userservice.dto.UpdateUser;
import com.socialapp.userservice.dto.UserResponse;
import com.socialapp.userservice.exception.EmailAlreadyExistException;
import com.socialapp.userservice.exception.UserNameAlreadyExistException;
import com.socialapp.userservice.exception.UserNotFoundException;
import com.socialapp.userservice.model.User;
import com.socialapp.userservice.repository.UserRepository;
import com.socialapp.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    //register user
    public UserResponse registerUser(User user){

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new EmailAlreadyExistException("Email '" + user.getEmail() + "' is already in use");
        }

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new UserNameAlreadyExistException("Username '" + user.getUsername() + "' is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .build();
    }

    //get user by username
    public UserResponse getUser(String username){
        User user= userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User with username "+ username +" not found"));
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .build();
    }

    //login user
    public LoginResponse loginUser(LoginRequest loginRequest){
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new UserNotFoundException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .build();

        return LoginResponse.builder()
                .token(token)
                .user(userResponse)
                .build();
    }

    public UserResponse updateUser(String username, UpdateUser updateUser) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));

        if (updateUser.getUsername() != null) {
            userRepository.findByUsername(updateUser.getUsername())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(user.getId()))
                            throw new UserNameAlreadyExistException("Username '" + updateUser.getUsername() + "' is already taken");
                    });
            user.setUsername(updateUser.getUsername());
        }
        if (updateUser.getEmail() != null) {
            userRepository.findByEmail(updateUser.getEmail())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(user.getId()))
                            throw new EmailAlreadyExistException("Email '" + updateUser.getEmail() + "' is already in use");
                    });
            user.setEmail(updateUser.getEmail());
        }
        if (updateUser.getName() != null) user.setName(updateUser.getName());
        if (updateUser.getPassword() != null) user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        if (updateUser.getBio() != null) user.setBio(updateUser.getBio());

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .build();
    }
}
