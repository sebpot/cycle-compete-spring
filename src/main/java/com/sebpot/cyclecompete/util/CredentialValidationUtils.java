package com.sebpot.cyclecompete.util;

import java.util.regex.Pattern;

public class CredentialValidationUtils {

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

}
