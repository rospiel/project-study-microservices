package com.study.microservices.studyapplication.domain.repository;

import com.study.microservices.studyapplication.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {
    List<Restaurant> search(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal);
    List<Restaurant> searchByFreeShippingAndName(String name);
}
