package com.scenius.mosaheb.services.impl;

import com.scenius.mosaheb.dto.JwtAuthenticationResponse;
import com.scenius.mosaheb.dto.RefreshTokenRequest;
import com.scenius.mosaheb.dto.SignInRequest;
import com.scenius.mosaheb.dto.SignUpRequest;
import com.scenius.mosaheb.entities.Role;
import com.scenius.mosaheb.entities.User;
import com.scenius.mosaheb.repositories.UserRepository;
import com.scenius.mosaheb.services.AuthenticationService;
import com.scenius.mosaheb.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPhone(signUpRequest.getPhone());
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getPhone(), signInRequest.getPassword()));
        User user = userRepository.findByPhone(signInRequest.getPhone())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile: " + signInRequest.getPhone()));

        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(token);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userPhone = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByPhone(userPhone)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile: " + userPhone));

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var token = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(token);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return jwtAuthenticationResponse;

        }

        return null;
    }
}
