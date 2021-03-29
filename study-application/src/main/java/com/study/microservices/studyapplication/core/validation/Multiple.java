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
@Constraint(validatedBy = { MultipleValidator.class })
public @interface Multiple {

    String message() default "invalid multiple";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /* value that will be necessary when the annotation is used */
    int value();

}
