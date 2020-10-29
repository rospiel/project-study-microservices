package com.study.microservices.studyapplication.infrastructure.repository.spec;

import com.study.microservices.studyapplication.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import static java.math.BigDecimal.ZERO;

public class RestaurantSpecs {

    public static Specification<Restaurant> withFreeShippingSpec() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("freightRate"), ZERO);
    }

    public static Specification<Restaurant> withSimilarNameSpec(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
}
