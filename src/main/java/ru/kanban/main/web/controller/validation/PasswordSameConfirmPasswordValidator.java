package ru.kanban.main.web.controller.validation;

import ru.kanban.main.dto.user.UserCreateDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type Password same confirm password validator.
 */
public class PasswordSameConfirmPasswordValidator implements ConstraintValidator<PasswordSameConfirmPassword, UserCreateDto> {
    @Override
    public boolean isValid(UserCreateDto value, ConstraintValidatorContext context) {
        return value.getConfirmPassword().equals(value.getPassword());
    }
}