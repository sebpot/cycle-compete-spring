package com.sebpot.cyclecompete.service;

import com.sebpot.cyclecompete.model.user.EditUserPasswordRequest;
import com.sebpot.cyclecompete.model.user.EditUserRequest;
import com.sebpot.cyclecompete.model.user.EditUserResponse;
import com.sebpot.cyclecompete.repository.UserRepository;
import com.sebpot.cyclecompete.util.CredentialValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public EditUserResponse changeUserDetails(EditUserRequest request, String token) throws Exception {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("An account with given email does not exist."));
        validateUserCredentials(request);
        user.setFirstname(request.getFirstname().strip());
        user.setLastname(request.getLastname().strip());
        userRepository.save(user);
        return EditUserResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }

    public void validateUserCredentials(EditUserRequest request) throws Exception {
        if (!CredentialValidationUtils.isNameValid(request.getFirstname().strip())) {
            throw new Exception("Incorrect firstname");
        }
        if (!CredentialValidationUtils.isNameValid(request.getLastname().strip())) {
            throw new Exception("Incorrect lastname");
        }
    }

    public void changeUserPassword(EditUserPasswordRequest request, String token) throws Exception {
        String email = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(email)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email, request.getOldPassword()
                    )
            );
        } catch(AuthenticationException e){
            throw new Exception("Given current password is incorrect");
        }
        if(!CredentialValidationUtils.isPasswordValid(request.getNewPassword())){
            throw new Exception("Invalid new password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteUser(String token) throws Exception {
        String email = jwtService.extractUsername(token);
        if(userRepository.findByEmail(email).isEmpty()){
            throw new ChangeSetPersister.NotFoundException();
        }
        userRepository.deleteByEmail(email);
    }
}
