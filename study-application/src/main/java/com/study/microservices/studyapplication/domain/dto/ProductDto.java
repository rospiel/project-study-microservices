package com.study.microservices.studyapplication.domain.dto;

import com.study.microservices.studyapplication.domain.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductDto {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean enable;

    private Restaurant restaurant;

}
