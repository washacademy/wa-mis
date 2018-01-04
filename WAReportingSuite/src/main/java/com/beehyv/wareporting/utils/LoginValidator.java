package com.beehyv.wareporting.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by beehyv on 21/3/17.
 */
public class LoginValidator implements Validator{
    public boolean supports(Class aClass) {
        return LoginUser.class.isAssignableFrom(aClass);
    }

    public void validate(Object object, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username.empty", "Please specify a username.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.empty", "Please specify a password.");
    }
}
