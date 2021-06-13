package com.study.microservices.studyapplication.api.controller.model.request.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.microservices.studyapplication.core.validation.Multiple;
import com.study.microservices.studyapplication.core.validation.ZeroFreightRateMustHaveItInRestaurantName;
import com.study.microservices.studyapplication.domain.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/* freightRate --> exactly name in the annotation, assignment is the name of the property from the class,
   with this name that the value is obtained from the object to start the validation, if a wrong name will
   result in http 500

   name --> the some of freightRate
   mandatoryDescription --> is not about a property of the object but the value we want to find at the name */
@ZeroFreightRateMustHaveItInRestaurantName(freightRate = "freightRate",
        restaurantName = "name", mandatoryDescription = "free delivery")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantRequest {

    /* @NotNull  just null */
    /* @NotEmpty  null and also empty */
    @NotBlank /* null, empty and also spaces */
    @JsonProperty("name")
    private String name;

    @DecimalMin(value = "0")
    /* @PositiveOrZero have the same behavior of a previous annotation */
    @Multiple(6)
    @JsonProperty("freightRate")
    private BigDecimal freightRate;

    @NotNull
    @JsonProperty("kitchenId")
    private Long kitchenId;

    @Valid
    @JsonProperty("address")
    private AddressRequest address;
}
