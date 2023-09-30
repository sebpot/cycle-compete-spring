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
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .token(jwtToken)
                .build();
    }

    public static void validateUserCredentials(RegisterRequest request) throws Exception{
        if (!isEmailValid(request.getEmail().strip())) {
            throw new Exception("Incorrect email format");
        }
        if (!isNameValid(request.getFirstname().strip())) {
            throw new Exception("Incorrect firstname");
        }
        if (!isNameValid(request.getLastname().strip())) {
            throw new Exception("Incorrect lastname");
        }
        // Don't strip password, because passwords can contain space
        if(!isPasswordValid(request.getPassword())){
            throw new Exception("Invalid password");
        }
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^(.+)@(.+)$");
    }

    public static boolean isNameValid(String name) {
        // \p{L} - Uppercase or lowercase letter from any language
        // Pattern pattern = Pattern.compile("(\\p{L})+", Pattern.UNICODE_CASE);
        // return pattern.matcher(name).matches();
        return !name.isBlank();
    }

    public static boolean isPasswordValid(String password) {
        final int MIN_LENGTH = 7;
        final int MAX_LENGTH = Integer.MAX_VALUE;

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        final Pattern lowerCase = Pattern.compile("\\p{Ll}", Pattern.UNICODE_CASE);
        final Pattern upperCase = Pattern.compile("\\p{Lu}", Pattern.UNICODE_CASE);
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
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .token(jwtToken)
                .build();
    }
}
