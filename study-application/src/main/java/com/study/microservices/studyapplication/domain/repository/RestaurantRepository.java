package com.study.microservices.studyapplication.domain.repository;

import com.study.microservices.studyapplication.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

    Optional<List<Restaurant>> searchByNameAndRestaurant(String name, Long restaurantId);

    @Query("from Restaurant r " +
           "join fetch r.kitchen " +
           "left join fetch r.paymentMethods")
    List<Restaurant> findAll();

    @Query("select new com.study.microservices.studyapplication.domain.model.Restaurant(r.id)" +
           "from Restaurant r where r.kitchen.id = :kitchenId")
    List<Restaurant> findAllByKitchen(Long kitchenId);

}
