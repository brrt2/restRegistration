package com.bartosz.pieczara.registration.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        boolean result = false;

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 100),
                new UppercaseCharacterRule(1),
                new LowercaseCharacterRule(1),
                new DigitCharacterRule(1)));

        RuleResult ruleResult = validator.validate(new PasswordData(password));
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