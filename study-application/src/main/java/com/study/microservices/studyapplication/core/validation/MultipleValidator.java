package com.study.microservices.studyapplication.core.validation;

import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

/* ConstraintValidator received the annotation that will use this validation
   and what kind of value we can use to validated, for example String or Char,
   in this case Number */
public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private String multipleNumber;

    @Override /* where we received the value passed at the annotation */
    public void initialize(Multiple constraintAnnotation) {
        this.multipleNumber = String.valueOf(constraintAnnotation.value());
    }

    @Override /* where we received the value that contain the annotation */
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean isValidNumber = nonNull(value) && NumberUtils.isCreatable(value.toString());
        if (isValidNumber) {
            BigDecimal decimalValue = NumberUtils.createBigDecimal(value.toString());
            BigDecimal decimalMultiple = NumberUtils.createBigDecimal(this.multipleNumber);
            return decimalValue.remainder(decimalMultiple).compareTo(BigDecimal.ZERO) == 0 ?
                    true : false;
        }

        return true;
    }
}
