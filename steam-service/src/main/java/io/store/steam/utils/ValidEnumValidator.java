package io.store.steam.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValidEnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null) return true;

        if(enumClass.isEnum()) {
            return Arrays.stream(enumClass.getEnumConstants())
                    .anyMatch(enumConstants -> enumConstants.name().equals(value.toString()));
        }

        return false;

    }
}
