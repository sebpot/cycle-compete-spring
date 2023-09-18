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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        final int MIN_LENGTH = 7;
        final int MAX_LENGTH = Integer.MAX_VALUE;

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        final Pattern lowerCase = Pattern.compile("[a-z]");
        final Pattern upperCase = Pattern.compile("[A-Z]");
        final Pattern numbers = Pattern.compile("[0-9]");
        final Pattern specials = Pattern.compile("[ !\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~]");

        boolean hasLower = lowerCase.matcher(password).find();
        boolean hasUpper = upperCase.matcher(password).find();
        boolean hasNumber = numbers.matcher(password).find();
        boolean hasSpecial = specials.matcher(password).find();

        // System.out.println("Validating password: " + password + "\nhasLower: " + hasLower +
        //        ", hasUpper: " + hasUpper + ", hasNumber: " + hasNumber + ", hasSpecial: " + hasSpecial);

        return hasLower && hasUpper && hasNumber && hasSpecial;
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
