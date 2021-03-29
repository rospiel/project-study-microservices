package com.study.microservices.studyapplication.core.validation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/* where it's possible to use */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
/* execution time */
@Retention(RUNTIME)
@Constraint(validatedBy = { })
/* using another annotation together */
@PositiveOrZero
public @interface FreightRate {

    /* to change a original message from a positiveOrZero */
    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    String message() default "{invalid.freight.rate}"; /* find on messages.properties */

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
