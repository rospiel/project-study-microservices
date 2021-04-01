package com.study.microservices.studyapplication.api.controller.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;

import java.math.BigDecimal;

public class RestaurantMixin {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("freightRate")
    private BigDecimal freightRate;

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    @JsonProperty("kitchen")
    private KitchenDto kitchen;
}
