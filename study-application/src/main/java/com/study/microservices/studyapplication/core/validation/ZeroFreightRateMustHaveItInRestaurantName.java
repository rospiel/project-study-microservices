package com.study.microservices.studyapplication.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/* Use just in Class, interface or enum */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = { DeliveryFreeRestaurantNameValidator.class })
public @interface ZeroFreightRateMustHaveItInRestaurantName {

    String message() default "Name description invalid for free delivery";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String freightRate();

    String restaurantName();

    /* this is the value that the name must have if the freight rate is zero */
    String mandatoryDescription();
}
