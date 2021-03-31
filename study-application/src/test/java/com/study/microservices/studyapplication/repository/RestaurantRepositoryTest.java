package com.study.microservices.studyapplication.repository;

import com.study.microservices.studyapplication.domain.model.Kitchen;
import com.study.microservices.studyapplication.domain.model.Restaurant;
import com.study.microservices.studyapplication.domain.repository.KitchenRepository;
import com.study.microservices.studyapplication.domain.repository.RestaurantRepository;
import com.study.microservices.studyapplication.util.DatabaseCleaner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private DatabaseCleaner cleaner;

    @Before
    public void setup() {
        cleaner.clearTables();
        setupDataBase();
    }

    @Test
    public void testFindAllByKitchenReturnEntityJustWithId() {
        List<Restaurant> restaurants = repository.findAllByKitchen(1L);
        assertThat(restaurants.size()).isEqualTo(2);
        restaurants.stream().forEach(restaurant -> {
            assertThat(restaurant.getAddress()).isNull();
            assertThat(restaurant.getKitchen()).isNull();
            assertThat(restaurant.getName()).isNull();
            assertThat(restaurant.getPaymentMethods().isEmpty()).isTrue();
            assertThat(restaurant.getProducts().isEmpty()).isTrue();
            assertThat(restaurant.getFreightRate()).isNull();
            assertThat(restaurant.getId()).isNotNull();
        });
    }

    private void setupDataBase() {
        Kitchen kitchen = kitchenRepository.save(new Kitchen(null, "American"));

        repository.save(new Restaurant("New York", ZERO, kitchen));
        repository.save(new Restaurant("Washington", ZERO, kitchen));
    }
}
