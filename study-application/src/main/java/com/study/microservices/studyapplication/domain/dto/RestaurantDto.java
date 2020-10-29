package com.study.microservices.studyapplication.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantDto {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private BigDecimal freightRate;

    private KitchenDto kitchen;

}
