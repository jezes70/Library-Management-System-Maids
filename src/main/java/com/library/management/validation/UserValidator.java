package com.library.management.validation;

import com.library.management.repositories.BookRepository;
import com.library.management.validation.anotations.ValidateAuthor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidator implements ConstraintValidator<ValidateAuthor, String> {
    private final BookRepository bookRepository;

    @Override
    public boolean isValid(String author, ConstraintValidatorContext constraintValidatorContext) {
        return !bookRepository.existsByAuthor(author);
    }
}