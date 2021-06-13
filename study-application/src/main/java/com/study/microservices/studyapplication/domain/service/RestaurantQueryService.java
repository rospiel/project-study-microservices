package com.study.microservices.studyapplication.domain.service;

import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantIncludeByDayAndStateFilter;
import com.study.microservices.studyapplication.domain.dto.RestaurantIncludeByDayAndState;
import com.study.microservices.studyapplication.domain.model.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections.CollectionUtils.addIgnoreNull;
/* Not necessarily a repository but is more safety to use because of native handle, since we're dealing with entities */
@Repository
public class RestaurantQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RestaurantIncludeByDayAndState> searchRestaurantIncludeByDayAndState(RestaurantIncludeByDayAndStateFilter filter, String timeOffset) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        /* essential to say what kind of object will be construct */
        CriteriaQuery query = builder.createQuery(RestaurantIncludeByDayAndState.class);
        /* from what table */
        Root table = query.from(Restaurant.class);
        List<Predicate> clauses = new ArrayList<>();

        /* here we have an example of how to use native functions from database, in this case de function is convert_tz */
        /* 1 -> the name of the native function */
        /* 2 -> what kind of object will be returned */
        /* 3 and beyond -> the parameters that the native function need to process */
        Expression functionConvertTzDateCreate = builder.function(
                "convert_tz", Date.class, table.get("dateCreate"), builder.literal("+00:00"), builder.literal(timeOffset));

        /* another example of native function, in this case note that we use the first native function as parameter */
        Expression functionDateDateCreate = builder.function("date", Date.class, functionConvertTzDateCreate);

        /* Normally not necessarily but as we use this column more than one place is more easy to maintenance */
        Path stateNameColumn = table.get("address").get("city").get("state").get("name");

        /* columns we need, obligatory to correspond the exactly constructor of the class we are expecting */
        CompoundSelection columns = builder.construct(RestaurantIncludeByDayAndState.class,
                stateNameColumn,
                functionDateDateCreate,
                builder.count(table.get("id")));

        /* Using the filters */
        addIgnoreNull(clauses, nonNull(filter.getCreationDate()) ?
                builder.greaterThanOrEqualTo(table.get("dateCreate"), filter.getCreationDate()) : null);

        addIgnoreNull(clauses, nonNull(filter.getEndCreationDate()) ?
                builder.lessThanOrEqualTo(table.get("dateCreate"), filter.getEndCreationDate()) : null);

        addIgnoreNull(clauses, nonNull(filter.getEnable()) ?
                builder.equal(table.get("enable"), filter.getEnable()) : null);

        addIgnoreNull(clauses, nonNull(filter.getStateName()) ?
                builder.equal(stateNameColumn, filter.getStateName()) : null);

        /* Adding the columns we want */
        query.select(columns);
        /* Adding the where clauses */
        query.where(clauses.toArray(new Predicate[0]));
        /* Adding group by, in this case we inform another native function create at the top */
        query.groupBy(functionDateDateCreate, stateNameColumn);


        return entityManager.createQuery(query).getResultList();
    }
}
