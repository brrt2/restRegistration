package com.bartosz.pieczara.registration.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final String ONLY_ALPHANUMERIC_REGEX = "^[A-Za-z0-9]+$";
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 40;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        boolean result = false;

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(MIN_USERNAME_LENGTH,MAX_USERNAME_LENGTH),
                new AllowedRegexRule(ONLY_ALPHANUMERIC_REGEX)));

        RuleResult ruleResult = validator.validate(new PasswordData(username));
        if (ruleResult.isValid()) {
            result = true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    validator.getMessages(ruleResult).get(0))
                    .addConstraintViolation();
        }

        return result;
    }
}
