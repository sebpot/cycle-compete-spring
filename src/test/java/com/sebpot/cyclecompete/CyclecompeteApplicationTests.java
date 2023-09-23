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
    public void isNameValidTest() {
        assertFalse(AuthService.isNameValid(""));
        assertFalse(AuthService.isNameValid("Knook123"));
        assertFalse(AuthService.isNameValid("23754345"));
        assertFalse(AuthService.isNameValid("Knook Horsie"));
        assertFalse(AuthService.isNameValid(" Knook"));
        assertFalse(AuthService.isNameValid("Knook "));
        assertFalse(AuthService.isNameValid(" Knook "));
        assertFalse(AuthService.isNameValid(" Błażej "));
        assertFalse(AuthService.isNameValid("Adrian Miński"));

        assertTrue(AuthService.isNameValid("Knooki"));
        assertTrue(AuthService.isNameValid("kNook"));
        assertTrue(AuthService.isNameValid("Błażej"));
        assertTrue(AuthService.isNameValid("Łukasz"));
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
        assertFalse(AuthService.isPasswordValid("łukasz123"));
        assertFalse(AuthService.isPasswordValid("Łukasz123"));
        assertFalse(AuthService.isPasswordValid("Łukasz123ęąść"));
        assertFalse(AuthService.isPasswordValid("Łukasz###ęąść"));
        assertFalse(AuthService.isPasswordValid("Łóś1$$"));

        assertTrue(AuthService.isPasswordValid("Admin12#"));
        assertTrue(AuthService.isPasswordValid("Administrator1337!!!!!"));
        assertTrue(AuthService.isPasswordValid("Knookie 127"));
        assertTrue(AuthService.isPasswordValid("Knookie69 BIG#CHONKER?!"));
        assertTrue(AuthService.isPasswordValid("AdrianMiński123#"));
        assertTrue(AuthService.isPasswordValid("Łukasz 123"));
        assertTrue(AuthService.isPasswordValid("Żę12345@@@@@"));
    }

}
