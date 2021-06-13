package com.study.microservices.studyapplication.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface Security {

    public @interface Kitchens {

        @PreAuthorize("hasAuthority('CHANGE_KITCHEN')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanInsert {}

        @PreAuthorize("hasAuthority('CHANGE_KITCHEN') and @personalSecurity.isKitchenNotAssociated(#kitchenId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanUpdate {}
    }

    public @interface Cities {

        @PreAuthorize("hasAuthority('SCOPE_email') and hasAuthority('SEARCH_CITY')")
        @PostAuthorize("'Osasco'.equals(returnObject.getBody().get(0).getName())")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanSearch {}
    }
}
