package com.skobelev.product.api.validations;

import com.skobelev.product.api.model.Category;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class SizeConstraintValidator implements ConstraintValidator<SizeConstraint, Set<Category>> {

    @Override
    public boolean isValid(Set<Category> value, ConstraintValidatorContext context) {
        return value.size() > 1 && value.size() <= 5;
    }
}
