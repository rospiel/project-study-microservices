package com.study.microservices.studyapplication.domain.repository;

import com.study.microservices.studyapplication.domain.dto.RestaurantPaymentMethodDto;
import com.study.microservices.studyapplication.domain.model.Restaurant;
import org.springframework.data.domain.Pageable;
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
           "join fetch r.address.city c " +
           "join fetch c.state s " +
           "join fetch r.kitchen k " +
           "left join fetch r.paymentMethods pm")
    List<Restaurant> findAll();

    @Query("select new com.study.microservices.studyapplication.domain.model.Restaurant(r.id)" +
           "from Restaurant r where r.kitchen.id = :kitchenId")
    List<Restaurant> findAllByKitchen(Long kitchenId);

    @Query("select new com.study.microservices.studyapplication.domain.model.Restaurant(r.id)" +
           "from Restaurant r " +
           "join r.paymentMethods pm " +
           "where pm.id = :paymentMethodId")
    List<Restaurant> findAllByPaymentMethod(Long paymentMethodId, Pageable pageable);

    @Query("select new com.study.microservices.studyapplication.domain.dto.RestaurantPaymentMethodDto(r.code, pm.description)" +
           "from Restaurant r " +
           "join r.paymentMethods pm " +
           "where r.code = :restaurantCode")
    List<RestaurantPaymentMethodDto> findAllPaymentMethod(String restaurantCode);

    Restaurant findByCode(String code);

}
