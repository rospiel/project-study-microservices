package com.study.microservices.studyapplication.infrastructure.repository;

import com.study.microservices.studyapplication.domain.model.Restaurant;
import com.study.microservices.studyapplication.domain.repository.RestaurantRepository;
import com.study.microservices.studyapplication.domain.repository.RestaurantRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static com.study.microservices.studyapplication.infrastructure.repository.spec.RestaurantSpecs.withFreeShippingSpec;
import static com.study.microservices.studyapplication.infrastructure.repository.spec.RestaurantSpecs.withSimilarNameSpec;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Lazy
    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> findAll() {
        return entityManager.createQuery(" from Restaurant", Restaurant.class).getResultList();
    }

    @Override
    public List<Restaurant> search(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal) {
        StringBuilder jpql = new StringBuilder("from Restaurant ")
                .append("where name like :name ")
                .append("and freightRate between :freightRateInitial and :freightRateFinal");

        return entityManager.createQuery(jpql.toString(), Restaurant.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("freightRateInitial", freightRateInitial)
                .setParameter("freightRateFinal", freightRateFinal)
                .getResultList();
    }

    @Override
    public List<Restaurant> searchByFreeShippingAndName(String name) {
        return restaurantRepository.findAll(withFreeShippingSpec().and(withSimilarNameSpec(name)));
    }

    public List<Restaurant> searchWithCriteria(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurant> criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);
        Root<Restaurant> root = criteriaQuery.from(Restaurant.class);

        Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + name + "%");
        Predicate predicateFreightRateInitial = criteriaBuilder.greaterThanOrEqualTo(root.get("freightRate"), freightRateInitial);
        Predicate predicateFreightRateFinal = criteriaBuilder.lessThanOrEqualTo(root.get("freightRate"), freightRateFinal);

        criteriaQuery.where(predicateName, predicateFreightRateInitial, predicateFreightRateFinal);

        TypedQuery<Restaurant> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public Restaurant findById(final Long id) {
        return entityManager.find(Restaurant.class, id);
    }

    @Transactional
    public Restaurant save(final Restaurant restaurant) {
        return entityManager.merge(restaurant);
    }

    @Transactional
    public void delete(final Restaurant restaurant) {
        Restaurant restaurantBase = findById(restaurant.getId());
        entityManager.remove(restaurantBase);
    }
}
