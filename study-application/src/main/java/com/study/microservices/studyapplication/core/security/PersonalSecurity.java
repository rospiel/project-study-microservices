package com.study.microservices.studyapplication.core.security;

import com.study.microservices.studyapplication.domain.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PersonalSecurity {

    private final RestaurantService restaurantService;

    public boolean isKitchenNotAssociated(Long kitchenId) {
        return restaurantService.searchByKitchen(kitchenId).get().isEmpty();
    }

    public String getNameFromUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = (Jwt) principal;
        return jwt.getClaims().get("name").toString();
    }
}
