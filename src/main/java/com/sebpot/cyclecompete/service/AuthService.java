package com.sebpot.cyclecompete.service;

import com.sebpot.cyclecompete.model.auth.AuthRequest;
import com.sebpot.cyclecompete.model.auth.AuthResponse;
import com.sebpot.cyclecompete.model.auth.RegisterRequest;
import com.sebpot.cyclecompete.model.User;
import com.sebpot.cyclecompete.model.UserRole;
import com.sebpot.cyclecompete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) throws Exception {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new Exception("Email is already in use");
        }
        validateUserCredentials(request);
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void validateUserCredentials(RegisterRequest request) throws Exception{
        if (!request.getEmail().matches("^(.+)@(.+)$")) {
            throw new Exception("Incorrect email format");
        }
        if (!request.getFirstname().matches("[a-zA-Z]*")) {
            throw new Exception("Incorrect firstname");
        }
        if (!request.getLastname().matches("[a-zA-Z]*")) {
            throw new Exception("Incorrect lastname");
        }
        if(request.getPassword().length() < 5) {
            throw new Exception("Password is too short");
        }
    }

    public AuthResponse authenticate(AuthRequest request) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );
        } catch(AuthenticationException e){
            throw new Exception("Given email or password are incorrect");
        }
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("An account with given email address does not exist"));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
