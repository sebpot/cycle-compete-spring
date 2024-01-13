package com.sebpot.cyclecompete.service;

import com.sebpot.cyclecompete.model.auth.AuthRequest;
import com.sebpot.cyclecompete.model.auth.AuthResponse;
import com.sebpot.cyclecompete.model.auth.RegisterRequest;
import com.sebpot.cyclecompete.model.user.User;
import com.sebpot.cyclecompete.model.user.UserRole;
import com.sebpot.cyclecompete.repository.UserRepository;
import com.sebpot.cyclecompete.util.CredentialValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .firstname(request.getFirstname().strip())
                .lastname(request.getLastname().strip())
                .email(request.getEmail().strip())
                // Don't strip password, because passwords can contain space
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
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .token(jwtToken)
                .role(user.getRole().toString())
                .build();
    }

    public void validateUserCredentials(RegisterRequest request) throws Exception{
        if (!CredentialValidationUtils.isEmailValid(request.getEmail().strip())) {
            throw new Exception("Incorrect email format");
        }
        if (!CredentialValidationUtils.isNameValid(request.getFirstname().strip())) {
            throw new Exception("Incorrect firstname");
        }
        if (!CredentialValidationUtils.isNameValid(request.getLastname().strip())) {
            throw new Exception("Incorrect lastname");
        }
        // Don't strip password, because passwords can contain space
        if(!CredentialValidationUtils.isPasswordValid(request.getPassword())){
            throw new Exception("Invalid password");
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
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .token(jwtToken)
                .role(user.getRole().toString())
                .build();
    }
}
