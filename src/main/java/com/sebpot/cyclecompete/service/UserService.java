package com.sebpot.cyclecompete.service;

import com.sebpot.cyclecompete.model.auth.RegisterRequest;
import com.sebpot.cyclecompete.model.user.EditUserPasswordRequest;
import com.sebpot.cyclecompete.model.user.EditUserRequest;
import com.sebpot.cyclecompete.model.user.EditUserResponse;
import com.sebpot.cyclecompete.model.user.User;
import com.sebpot.cyclecompete.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public EditUserResponse changeUserDetails(EditUserRequest request) throws Exception {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        validateUserCredentials(request);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        userRepository.save(user);
        return EditUserResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }

    public static void validateUserCredentials(EditUserRequest request) throws Exception {
        if (!isNameValid(request.getFirstname().strip())) {
            throw new Exception("Incorrect firstname");
        }
        if (!isNameValid(request.getLastname().strip())) {
            throw new Exception("Incorrect lastname");
        }
    }

    public static boolean isNameValid(String name) {
        // \p{L} - Uppercase or lowercase letter from any language
        // Pattern pattern = Pattern.compile("(\\p{L})+", Pattern.UNICODE_CASE);
        // return pattern.matcher(name).matches();
        return !name.isBlank();
    }

    public Void changeUserPassword(EditUserPasswordRequest request) throws Exception {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getOldPassword()
                    )
            );
        } catch(AuthenticationException e){
            throw new Exception("Given current password is incorrect");
        }
        if(!isPasswordValid(request.getNewPassword())){
            throw new Exception("Invalid new password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return null;
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

    public Void deleteUser(String email) throws Exception {
        if(userRepository.findByEmail(email).isEmpty()){
            throw new ChangeSetPersister.NotFoundException();
        }
        userRepository.deleteByEmail(email);
        return null;
    }
}
