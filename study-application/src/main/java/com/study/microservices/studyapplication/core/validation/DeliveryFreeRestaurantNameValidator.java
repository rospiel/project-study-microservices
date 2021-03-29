package com.study.microservices.studyapplication.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static java.util.Objects.nonNull;

/* <annotation created, kind of object can use> */
public class DeliveryFreeRestaurantNameValidator implements
        ConstraintValidator<ZeroFreightRateMustHaveItInRestaurantName, Object> {

    private String freightRate;
    private String restaurantName;
    private String mandatoryDescription;

    @Override
    public void initialize(ZeroFreightRateMustHaveItInRestaurantName constraintAnnotation) {
        this.freightRate = constraintAnnotation.freightRate();
        this.restaurantName = constraintAnnotation.restaurantName();
        this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
    }

    @Override
    public boolean isValid(Object valueToValidate, ConstraintValidatorContext context) {

        try {
            BigDecimal freightRate = (BigDecimal) BeanUtils.getPropertyDescriptor(valueToValidate.getClass(),
                    this.freightRate).getReadMethod().invoke(valueToValidate);

            String restaurantName = (String) BeanUtils.getPropertyDescriptor(valueToValidate.getClass(),
                    this.restaurantName).getReadMethod().invoke(valueToValidate);

            boolean freeDelivery = nonNull(freightRate) && freightRate.compareTo(BigDecimal.ZERO) == 0;
            return !freeDelivery ||
                    restaurantName.toLowerCase().contains(mandatoryDescription.toLowerCase());

        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
