package com.sebpot.cyclecompete.service;

import com.sebpot.cyclecompete.model.user.EditUserPasswordRequest;
import com.sebpot.cyclecompete.model.user.EditUserRequest;
import com.sebpot.cyclecompete.model.user.EditUserResponse;
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

import static com.sebpot.cyclecompete.service.AuthService.isNameValid;
import static com.sebpot.cyclecompete.service.AuthService.isPasswordValid;

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
        user.setFirstname(request.getFirstname().strip());
        user.setLastname(request.getLastname().strip());
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

    public Void deleteUser(String email) throws Exception {
        if(userRepository.findByEmail(email).isEmpty()){
            throw new ChangeSetPersister.NotFoundException();
        }
        userRepository.deleteByEmail(email);
        return null;
    }
}
