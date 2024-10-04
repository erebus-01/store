package io.store.user.utils.validator.phone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static io.store.user.utils.Constant.NUMBER_MULTIPLE_REGEX;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return Pattern.matches(NUMBER_MULTIPLE_REGEX, phone);
    }
}
