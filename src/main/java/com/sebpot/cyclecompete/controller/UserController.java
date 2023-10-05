package com.sebpot.cyclecompete.controller;

import com.sebpot.cyclecompete.model.user.EditUserPasswordRequest;
import com.sebpot.cyclecompete.model.user.EditUserRequest;
import com.sebpot.cyclecompete.model.user.EditUserResponse;
import com.sebpot.cyclecompete.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("")
    public ResponseEntity<EditUserResponse> editUserDetails(
            @RequestBody EditUserRequest request,
            @RequestHeader("Authorization") String authHeader
    ) throws Exception {
        String token = authHeader.substring(7);
        return ResponseEntity.ok(userService.changeUserDetails(request, token));
    }

    @PutMapping("/password")
    public ResponseEntity<?> editUserPassword(
            @RequestBody EditUserPasswordRequest request,
            @RequestHeader("Authorization") String authHeader
    ) throws Exception {
        String token = authHeader.substring(7);
        userService.changeUserPassword(request, token);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAccount(
            @RequestHeader("Authorization") String authHeader
    ) throws Exception {
        String token = authHeader.substring(7);
        userService.deleteUser(token);
        return ResponseEntity.ok().build();
    }
}
