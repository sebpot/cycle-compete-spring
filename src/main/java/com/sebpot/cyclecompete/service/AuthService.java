package com.sebpot.cyclecompete.service;

import com.sebpot.cyclecompete.model.auth.AuthRequest;
import com.sebpot.cyclecompete.model.auth.AuthResponse;
import com.sebpot.cyclecompete.model.auth.RegisterRequest;
import com.sebpot.cyclecompete.model.User;
import com.sebpot.cyclecompete.model.UserRole;
import com.sebpot.cyclecompete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
        if(!validatePassword(request.getPassword())){
            throw new Exception("Invalid password");
        }
    }

    private boolean validatePassword(String password) {
        String letters = "qwertyuiopasdfghjklzxcvbnm";
        String uCLetters = letters.toUpperCase();
        String numbers = "1234567890";
        String specials = "`~!@#$%^&*()-_ =+[{]}|\\;:,<.>/?\"";
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for(int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (letters.contains(String.valueOf(c))) {
                hasLower = true;
            } else if(uCLetters.contains(String.valueOf(c))) {
                hasUpper = true;
            } else if(numbers.contains(String.valueOf(c))) {
                hasNumber = true;
            } else if(specials.contains(String.valueOf(c))) {
                hasSpecial = true;
            }
        }
        return hasLower && hasUpper && hasNumber && !hasSpecial && password.length() > 7 && password.length() < 17;
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
