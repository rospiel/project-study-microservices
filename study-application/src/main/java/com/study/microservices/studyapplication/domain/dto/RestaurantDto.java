package com.study.microservices.studyapplication.domain.dto;

import com.study.microservices.studyapplication.core.validation.Groups;
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
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;

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
public class RestaurantDto {

    @EqualsAndHashCode.Include
    private Long id;

    /* @NotNull  just null */
    /* @NotEmpty  null and also empty */
    @NotBlank /* null, empty and also spaces */
    private String name;

    @DecimalMin(value = "0")
    /* @PositiveOrZero have the same behavior of a previous annotation */
    @Multiple(6)
    private BigDecimal freightRate;

    @Valid
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.IncludeRestaurant.class)
    private KitchenDto kitchen;

}
