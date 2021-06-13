package com.study.microservices.studyapplication.core.jmapper;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import com.study.microservices.studyapplication.domain.dto.RestaurantDto;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.dto.PaymentMethodDto;
import com.study.microservices.studyapplication.domain.dto.AddressDto;
import com.study.microservices.studyapplication.domain.dto.CityDto;
import com.study.microservices.studyapplication.domain.dto.StateDto;
import com.study.microservices.studyapplication.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.List;

import static com.googlecode.jmapper.api.JMapperAPI.mappedClass;
import static com.googlecode.jmapper.api.JMapperAPI.global;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class RestaurantConverter {

    private final JMapperAPI jMapperAPI;
    private JMapper<RestaurantDto, Restaurant> restaurant;
    private JMapper<Restaurant, RestaurantDto> dto;

    @PostConstruct
    private void setUp() {
        configEntityRelationship();
        configForDto();
        /* After to set up the dto we create a instance of Jmapper, here we pass the class we want and from what class */
        /* In this case we want a dto from a entity restaurant */
        restaurant = new JMapper(RestaurantDto.class, Restaurant.class, jMapperAPI);
        configForEntity();
        /* In this case we want a entity restaurant from a dto */
        dto = new JMapper(Restaurant.class, RestaurantDto.class, jMapperAPI);
    }

    /*
     * Here we add the entity to be mapped, global because all the properties need to convert
     * */
    private void configForEntity() {
        jMapperAPI.add(mappedClass(Restaurant.class).add(global()));
    }

    /*
    * Here we add a dto to be mapped
    * */
    private void configForDto() {
        jMapperAPI.add(mappedClass(RestaurantDto.class).add(global()));
    }

    /*
    * This is when we have others domain classes used by this of the converter, we must
    * ask to convert to
    * */
    private void configEntityRelationship() {
        jMapperAPI.add(mappedClass(KitchenDto.class).add(global()))
                .add(mappedClass(PaymentMethodDto.class).add(global()))
                .add(mappedClass(AddressDto.class).add(global()))
                .add(mappedClass(CityDto.class).add(global()))
                .add(mappedClass(StateDto.class).add(global()));
    }

    public Restaurant convert(RestaurantDto dto) {
        return isNull(dto) ? null : (Restaurant) this.dto.getDestination(dto);
    }

    public List<Restaurant> convertListRestaurant(List<RestaurantDto> dtos) {
        return isNull(dtos) || isEmpty(dtos) ? emptyList() :
                dtos.stream().map(this::convert).collect(toList());
    }

    public RestaurantDto convert(Restaurant restaurant) {
        return isNull(restaurant) ? null : (RestaurantDto) this.restaurant.getDestination(restaurant);
    }

    public List<RestaurantDto> convertListRestaurantDto(List<Restaurant> restaurants) {
        return isNull(restaurants) || isEmpty(restaurants) ? emptyList() :
                restaurants.stream().map(this::convert).collect(toList());
    }
}
