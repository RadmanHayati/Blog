package com.scenius.mosaheb.services;

import com.scenius.mosaheb.dto.JwtAuthenticationResponse;
import com.scenius.mosaheb.dto.RefreshTokenRequest;
import com.scenius.mosaheb.dto.SignInRequest;
import com.scenius.mosaheb.dto.SignUpRequest;
import com.scenius.mosaheb.entities.User;

public interface AuthenticationService {
     User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
