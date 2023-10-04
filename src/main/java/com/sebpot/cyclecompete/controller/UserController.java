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

    @Autowired
    private UserService userService;

    @PutMapping("")
    public ResponseEntity<EditUserResponse> editUserDetails(
            @RequestBody EditUserRequest request
    ) throws Exception {
        return ResponseEntity.ok(userService.changeUserDetails(request));
    }

    @PutMapping("/password")
    public ResponseEntity<?> editUserPassword(
            @RequestBody EditUserPasswordRequest request
    ) throws Exception {
        return ResponseEntity.ok(userService.changeUserPassword(request));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteAccount(
            @PathVariable String email
    ) throws Exception {
        return ResponseEntity.ok(userService.deleteUser(email));
    }
}
