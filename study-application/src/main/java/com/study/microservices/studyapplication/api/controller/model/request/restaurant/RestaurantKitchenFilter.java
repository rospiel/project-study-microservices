package com.study.microservices.studyapplication.api.controller.model.request.restaurant;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class RestaurantKitchenFilter {

    private String restaurantName;
    private Boolean restaurantEnable;
    private OffsetDateTime initialDate;
    private OffsetDateTime finalDate;
    private String stateName;
    private String cityName;
}
