package com.study.microservices.studyapplication.integration;

import com.study.microservices.studyapplication.api.controller.model.request.restaurant.AddressRequest;
import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantRequest;
import com.study.microservices.studyapplication.api.controller.model.response.restaurant.RestaurantResponse;
import com.study.microservices.studyapplication.domain.dto.CityDto;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.dto.StateDto;
import com.study.microservices.studyapplication.domain.exception.EntityAlreadyInUseException;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import com.study.microservices.studyapplication.domain.service.CityService;
import com.study.microservices.studyapplication.domain.service.KitchenService;
import com.study.microservices.studyapplication.domain.service.RestaurantService;
import com.study.microservices.studyapplication.domain.service.StateService;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.study.microservices.studyapplication.util.DatabaseCleaner;

import javax.validation.ConstraintViolationException;

import static java.lang.String.format;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/* to ensure all injections */
@RunWith(SpringRunner.class)
/* to use features from spring in our tests */
@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
@WithMockUser
public class KitchenIntegrationIT {

    @Autowired
    private KitchenService service;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private DatabaseCleaner cleaner;

    @Before
    public void setup() {
        cleaner.clearTables();
    }

    @Test
    public void testInsertKitchenSuccess() {
        KitchenDto kitchen = new KitchenDto(null, "Portuguese");
        KitchenDto kitchenSaved = service.save(kitchen);

        assertThat(kitchenSaved).isNotNull();
        assertThat(kitchenSaved.getId()).isNotNull();
        assertThat(kitchenSaved.getName()).isEqualTo(kitchen.getName());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testInsertKitchenWithoutNameNotAllow() {
        KitchenDto kitchenWithoutName = new KitchenDto(null, EMPTY);
        KitchenDto kitchenSaved = service.save(kitchenWithoutName);
    }

    @Test
    public void testDeleteKitchenInUseNotAllow() {
        KitchenDto kitchenSaved = service.save(new KitchenDto(null, "Mexican"));
        StateDto stateDto = stateService.save(new StateDto(null, "stateName"));
        CityDto cityDto = cityService.save(new CityDto(null, "cityName", stateDto));

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setCityId(cityDto.getId());
        addressRequest.setComplement("");
        addressRequest.setNeighborhood("Baltimore");
        addressRequest.setNumber("7");
        addressRequest.setStreet("3612  Five Points");
        addressRequest.setZipCode("21202");
        restaurantService.save(new RestaurantRequest("São Paulo",
                NumberUtils.createBigDecimal("6"), kitchenSaved.getId(), addressRequest));


        assertThatThrownBy(() -> service.delete(kitchenSaved.getId()))
                .isInstanceOf(EntityAlreadyInUseException.class)
                .hasMessage(format("There is/are restaurant(s) associate a kitchen of id.: %s", kitchenSaved.getId()));
    }

    @Test
    public void testDeleteKitchenNonexistentNotAllow() {
        KitchenDto kitchenNonexistent = new KitchenDto(Long.parseLong("9999999999"), "Mexican");
        assertThatThrownBy(() -> service.delete(kitchenNonexistent.getId()))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessage(format("Kitchen of id %s not found.", kitchenNonexistent.getId()));
    }

}
