package com.study.microservices.studyapplication.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.microservices.studyapplication.api.controller.model.response.view.restaurant.RestaurantView;
import lombok.Data;

@Data
public class AddressDto {

    @JsonView({RestaurantView.Resume.class})
    private String zipCode;

    @JsonView({RestaurantView.Resume.class})
    private String street;

    @JsonView({RestaurantView.Resume.class})
    private String number;

    @JsonView({RestaurantView.Resume.class})
    private String complement;

    @JsonView({RestaurantView.Resume.class})
    private String neighborhood;

    @JsonView({RestaurantView.Resume.class})
    private CityDto city;

}
