package ru.kanban.main.web.controller.validation;

import ru.kanban.main.dto.user.UserCreateDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordNotContainsEmailValidator implements ConstraintValidator<PasswordNotContainsEmail, UserCreateDto> {
    @Override
    public boolean isValid(UserCreateDto userCreateDto, ConstraintValidatorContext context) {
        String email = userCreateDto.getEmail();
        int dotIndex = email.indexOf(".");

        if (dotIndex != -1) {
            email = email.substring(0, dotIndex);
        }

        return !userCreateDto.getPassword().contains(email);
    }
}