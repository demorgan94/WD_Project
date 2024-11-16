package com.dm.wd_backend.presentation.product.dto.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class QuantityValidator implements ConstraintValidator<Quantity, Integer> {

    /**
     * Validates the given quantity. Returns true if the quantity is valid, false
     * otherwise. A quantity is valid if it is greater than 0.
     *
     * @param quantity the quantity to validate
     * @param context  the context in which the validation is being performed
     * @return true if the quantity is valid, false otherwise
     */
    @Override
    public boolean isValid(Integer quantity, ConstraintValidatorContext context) {
        if (quantity == null) return true; // Ignore if field is null in update request
        return quantity >= 0;
    }
}
