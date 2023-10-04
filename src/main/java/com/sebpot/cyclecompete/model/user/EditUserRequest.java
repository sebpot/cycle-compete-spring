package com.sebpot.cyclecompete.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {
    private String firstname;
    private String lastname;
    private String email;
}
