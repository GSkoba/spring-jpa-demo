package com.skobelev.product.api.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = SizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface SizeConstraint {
    String message() default "The input list cannot contain less than 1 item";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
