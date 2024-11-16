package com.dm.wd_backend.presentation.product.dto.validations;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PriceValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Price {
    String message() default "Price must be greater than or equal to 0";

    Class<?>[] groups() default {};

    Class<? extends Constraint>[] payload() default {};
}
