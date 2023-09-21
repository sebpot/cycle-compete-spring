package com.sebpot.cyclecompete;

import com.sebpot.cyclecompete.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CyclecompeteApplicationTests {

    @Test
    public void testIsEmailValidTest() {
        assertFalse(AuthService.isEmailValid(""));
        assertFalse(AuthService.isEmailValid("@"));
        assertFalse(AuthService.isEmailValid("test"));
        assertFalse(AuthService.isEmailValid("@test.com"));
        assertFalse(AuthService.isEmailValid("test@"));

        assertTrue(AuthService.isEmailValid("test@test.com"));
        assertTrue(AuthService.isEmailValid("admin@admin.com"));
        assertTrue(AuthService.isEmailValid("admin@admin"));
    }

    @Test
    public void isFirstNameValidTest() {
        assertFalse(AuthService.isFirstNameValid(""));
        assertFalse(AuthService.isFirstNameValid("Knook123"));
        assertFalse(AuthService.isFirstNameValid("23754345"));
        assertFalse(AuthService.isFirstNameValid("Knook Horsie"));
        assertFalse(AuthService.isFirstNameValid(" Knook"));
        assertFalse(AuthService.isFirstNameValid("Knook "));
        assertFalse(AuthService.isFirstNameValid(" Knook "));

        assertTrue(AuthService.isFirstNameValid("Knooki"));
        assertTrue(AuthService.isFirstNameValid("kNook"));
    }

    @Test
    public void isLastNameValidTest() {
        assertFalse(AuthService.isLastNameValid(""));
        assertFalse(AuthService.isLastNameValid("Knook123"));
        assertFalse(AuthService.isLastNameValid("23754345"));
        assertFalse(AuthService.isLastNameValid("Knook Horsie"));
        assertFalse(AuthService.isLastNameValid(" Knook"));
        assertFalse(AuthService.isLastNameValid("Knook "));
        assertFalse(AuthService.isLastNameValid(" Knook "));

        assertTrue(AuthService.isLastNameValid("Knooki"));
        assertTrue(AuthService.isLastNameValid("kNook"));
    }

    @Test
    public void isPasswordValidTest() {
        assertFalse(AuthService.isPasswordValid(""));
        assertFalse(AuthService.isPasswordValid("aaa"));
        assertFalse(AuthService.isPasswordValid("admin"));
        assertFalse(AuthService.isPasswordValid("adminadminadmin"));
        assertFalse(AuthService.isPasswordValid("Admin"));
        assertFalse(AuthService.isPasswordValid("AdminADMINADMIN"));
        assertFalse(AuthService.isPasswordValid("Admin1"));
        assertFalse(AuthService.isPasswordValid("Admin123"));
        assertFalse(AuthService.isPasswordValid("Admin1234567890"));
        assertFalse(AuthService.isPasswordValid("Admin!!!"));
        assertFalse(AuthService.isPasswordValid("Admin!?@#$%^&*()"));
        assertFalse(AuthService.isPasswordValid("ADMIN123$"));
        assertFalse(AuthService.isPasswordValid("ADMIN123$?!@??!@#?!@#"));
        assertFalse(AuthService.isPasswordValid("xXx!big$$$admin$$$money!xXx"));

        assertTrue(AuthService.isPasswordValid("Admin12#"));
        assertTrue(AuthService.isPasswordValid("Administrator1337!!!!!"));
        assertTrue(AuthService.isPasswordValid("Knookie 127"));
        assertTrue(AuthService.isPasswordValid("Knookie69 BIG#CHONKER?!"));
    }

}
