package com.study.microservices.studyapplication.api.controller.model.request.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.microservices.studyapplication.core.validation.Multiple;
import com.study.microservices.studyapplication.core.validation.ZeroFreightRateMustHaveItInRestaurantName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ZeroFreightRateMustHaveItInRestaurantName(freightRate = "freightRate",
        restaurantName = "name", mandatoryDescription = "free delivery")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantPutRequest {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @DecimalMin(value = "0")
    @Multiple(6)
    @JsonProperty("freightRate")
    private BigDecimal freightRate;

    @NotNull
    @JsonProperty("kitchenId")
    private Long kitchenId;

    @Valid
    @JsonProperty("address")
    private AddressPutRequest address;
}
