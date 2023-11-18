package com.sebpot.cyclecompete.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class CredentialValidationUtilsTests {

    @ParameterizedTest
    @ValueSource(strings = {
            "test@test.com",
            "admin@admin.com",
            "admin@admin"
    })
    public void isEmailValid_whenEmailIsValid_thenReturnTrue(String validEmail) {
        assertTrue(CredentialValidationUtils.isEmailValid(validEmail));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", "@", "test", "@test.com", "test@"
    })
    public void isEmailValid_whenEmailIsInvalid_thenReturnFalse(String invalidEmail) {
        assertFalse(CredentialValidationUtils.isEmailValid(invalidEmail));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Knook",
            "Błażej",
            "Adrian Miński",
            "Kowalska-Nowak",
            "Guðmundsdóttir",
            "José Carreño Quiñones"
    })
    public void isNameValid_whenNameIsValid_thenReturnTrue(String validName) {
        assertTrue(CredentialValidationUtils.isNameValid(validName));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", "    "
    })
    public void isNameValid_whenNameIsEmptyOrWhitespace_thenReturnFalse(String invalidName) {
        assertFalse(CredentialValidationUtils.isNameValid(invalidName));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Admin12#",
            "Administrator1337!!!!!",
            "Knookie 127",
            "Knookie69 BIG#CHONKER?!",
            "Łukasz 123",
            "José12345@@@@@"
    })
    public void isPasswordValid_whenPasswordIsValid_thenReturnTrue(String validPassword) {
        assertTrue(CredentialValidationUtils.isPasswordValid(validPassword));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "admin",
            "Admin",
            "Admin1",
            "Ad123!",
            "Łóś1$$",
    })
    public void isPasswordValid_whenPasswordIsTooShort_thenReturnFalse(String invalidPassword) {
        assertFalse(CredentialValidationUtils.isPasswordValid(invalidPassword));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "adminadminadmin",
            "admin123@@",
            "łukasz123",
            "xx!big$$$admin$$$money!xx",
    })
    public void isPasswordValid_whenPasswordHasNoUpperCaseLetters_thenReturnFalse(String invalidPassword) {
        assertFalse(CredentialValidationUtils.isPasswordValid(invalidPassword));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ADMIN123$",
            "ADMIN123$?!@??!@#?!@#",
    })
    public void isPasswordValid_whenPasswordHasNoLowerCaseLetters_thenReturnFalse(String invalidPassword) {
        assertFalse(CredentialValidationUtils.isPasswordValid(invalidPassword));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "AdminADMINADMIN",
            "Admin!!!",
            "Admin!?@#$%^&*()",
            "Łukasz###ęąść",
            "xXx!big$$$admin$$$money!xXx",
    })
    public void isPasswordValid_whenPasswordHasNoNumbers_thenReturnFalse(String invalidPassword) {
        assertFalse(CredentialValidationUtils.isPasswordValid(invalidPassword));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Admin123",
            "Admin1234567890",
            "łukasz123",
            "Łukasz123",
            "Łukasz123ęąść",
    })
    public void isPasswordValid_whenPasswordHasNoSpecialCharacters_thenReturnFalse(String invalidPassword) {
        assertFalse(CredentialValidationUtils.isPasswordValid(invalidPassword));
    }
}
