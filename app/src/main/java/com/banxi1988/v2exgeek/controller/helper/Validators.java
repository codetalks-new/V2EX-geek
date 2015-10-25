package com.banxi1988.v2exgeek.controller.helper;

/**
 * Created by banxi on 15/10/24.
 */
public class Validators {
    public static boolean isEmailValid(CharSequence email) {
        return email != null && email.toString().contains("@");
    }

    public static boolean isPasswordValid(CharSequence password) {
        return password != null && password.length() > 4;
    }
}
