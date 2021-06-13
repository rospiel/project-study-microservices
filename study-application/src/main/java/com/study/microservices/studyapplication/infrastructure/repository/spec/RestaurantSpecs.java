package com.study.microservices.studyapplication.infrastructure.repository.spec;

import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantKitchenFilter;
import com.study.microservices.studyapplication.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static org.apache.commons.collections.CollectionUtils.addIgnoreNull;

public class RestaurantSpecs {

    private static final String LIKE = "%%%s%%";

    public static Specification<Restaurant> withFreeShippingSpec() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("freightRate"), ZERO);
    }

    public static Specification<Restaurant> withSimilarNameSpec(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Restaurant> filterRestaurant(RestaurantKitchenFilter filter) {
        return (table, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            /* Fetch to be sure that just one select will be executed  */
            avoidFetchWhenCountForPagination(table, query);
            if (isNull(filter)) {
                return builder.and(predicates.toArray(new Predicate[0]));
            }

            addIgnoreNull(predicates, isNull(filter.getCityName()) ? null :
                    builder.equal(table.get("address").get("city").get("name"), filter.getCityName()));

            addIgnoreNull(predicates, isNull(filter.getStateName()) ? null :
                    builder.equal(table.get("address").get("city").get("state").get("name"), filter.getStateName()));

            addIgnoreNull(predicates, isNull(filter.getRestaurantEnable()) ? null :
                    builder.equal(table.get("enable"), filter.getRestaurantEnable()));

            addIgnoreNull(predicates, isNull(filter.getRestaurantName()) ? null :
                    builder.like(table.get("name"), format(LIKE, filter.getRestaurantName())));

            /* When we are with relationship ...One just get is essential, for ...Many is join */
            addIgnoreNull(predicates, isNull(filter.getInitialDate()) || isNull(filter.getFinalDate()) ? null :
                    builder.between(table.get("dateCreate"), filter.getInitialDate(), filter.getFinalDate()));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void avoidFetchWhenCountForPagination(Root table, CriteriaQuery query) {
        if (Restaurant.class.equals(query.getResultType())) {
            table.fetch("kitchen");
            table.fetch("address").fetch("city").fetch("state");
        }
    }
}
