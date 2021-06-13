package com.study.microservices.studyapplication.repository;

import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantIncludeByDayAndStateFilter;
import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantKitchenFilter;
import com.study.microservices.studyapplication.domain.dto.RestaurantIncludeByDayAndState;
import com.study.microservices.studyapplication.domain.model.Address;
import com.study.microservices.studyapplication.domain.model.City;
import com.study.microservices.studyapplication.domain.model.Kitchen;
import com.study.microservices.studyapplication.domain.model.PaymentMethod;
import com.study.microservices.studyapplication.domain.model.Restaurant;
import com.study.microservices.studyapplication.domain.model.State;
import com.study.microservices.studyapplication.domain.repository.CityRepository;
import com.study.microservices.studyapplication.domain.repository.KitchenRepository;
import com.study.microservices.studyapplication.domain.repository.PaymentMethodRepository;
import com.study.microservices.studyapplication.domain.repository.RestaurantRepository;
import com.study.microservices.studyapplication.domain.repository.StateRepository;
import com.study.microservices.studyapplication.domain.service.RestaurantQueryService;
import com.study.microservices.studyapplication.infrastructure.repository.spec.RestaurantSpecs;
import com.study.microservices.studyapplication.util.DatabaseCleaner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

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
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private RestaurantQueryService restaurantQueryService;

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
            assertThat(restaurant.getFreightRate()).isNull();
            assertThat(restaurant.getId()).isNotNull();
        });
    }

    @Test
    public void testRestaurantSpecsFilterRestaurant() {
        RestaurantKitchenFilter filter = new RestaurantKitchenFilter();
        filter.setRestaurantEnable(true);
        filter.setInitialDate(OffsetDateTime.of(LocalDateTime.of(2021, 01, 01, 01, 00),
                ZoneOffset.ofHoursMinutes(-3, 00)));
        filter.setFinalDate(OffsetDateTime.of(LocalDateTime.of(2021, 04, 25, 23, 00),
                ZoneOffset.ofHoursMinutes(-3, 00)));

        Page<Restaurant> restaurants = repository.findAll(RestaurantSpecs.filterRestaurant(filter), PageRequest.of(1, 1));
        assertThat(restaurants).isNotNull();
    }

    @Test
    public void testSearchRestaurantIncludeByDayAndStateFilter() {
        RestaurantIncludeByDayAndStateFilter filter = new RestaurantIncludeByDayAndStateFilter();
        filter.setEnable(true);
        filter.setCreationDate( (OffsetDateTime.now().minusDays(1)) );
        filter.setEndCreationDate( (OffsetDateTime.now().plusDays(1)) );
        filter.setStateName("São Paulo");

        List<RestaurantIncludeByDayAndState> restaurants = restaurantQueryService.searchRestaurantIncludeByDayAndState(filter, "+00:00");
        assertThat(restaurants).isNotNull();
    }

    private void setupDataBase() {
        State state = stateRepository.save(new State(null, "São Paulo"));
        State state2 = stateRepository.save(new State(null, "Rio de Janeiro"));
        City city = cityRepository.save(new City(null, "cityName", state));
        City city2 = cityRepository.save(new City(null, "cityName", state2));

        Address address = new Address();
        address.setCity(city);
        address.setComplement("");
        address.setNeighborhood("Baltimore");
        address.setNumber("7");
        address.setStreet("3612  Five Points");
        address.setZipCode("21202");

        Kitchen kitchen = kitchenRepository.save(new Kitchen(null, "American"));
        PaymentMethod creditCard = paymentMethodRepository.save(new PaymentMethod(null, "credit Card"));
        PaymentMethod cash = paymentMethodRepository.save(new PaymentMethod(null, "cash"));

        Restaurant restaurant = new Restaurant("New York", ZERO, kitchen);
        restaurant.setPaymentMethods(Set.of(creditCard, cash));
        restaurant.setAddress(address);
        repository.save(restaurant);

        restaurant = new Restaurant("Washington", ZERO, kitchen);
        restaurant.setPaymentMethods(Set.of(cash));
        address.setCity(city2);
        restaurant.setAddress(address);
        repository.save(restaurant);
    }
}
