package com.dm.wd_backend.presentation.product.dto.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PriceValidator implements ConstraintValidator<Price, Double> {

    /**
     * Determines if the given price is valid.
     *
     * A price is valid if it is greater than 0.
     *
     * @param price the price to check
     * @param context the context in which the validation is being performed
     * @return true if the price is valid, false otherwise
     */
    @Override
    public boolean isValid(Double price, ConstraintValidatorContext context) {
        if (price == null) return true; // Ignore if field is null in update request
        return price >= 0;
    }
}
