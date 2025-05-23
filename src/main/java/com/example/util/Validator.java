package com.example.util;

public class Validator {

    public static void validateEmail(String email) {
        if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            throw new IllegalArgumentException("Invalid email.");
        }
    }
}