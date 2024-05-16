package com.library.management.validation.anotations;



import com.library.management.validation.UserValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserValidator.class)
public @interface ValidateAuthor {

    public String message() default "Author already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}