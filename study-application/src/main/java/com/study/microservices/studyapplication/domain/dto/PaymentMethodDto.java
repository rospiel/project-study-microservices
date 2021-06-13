package com.study.microservices.studyapplication.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.microservices.studyapplication.api.controller.model.response.view.restaurant.RestaurantView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class PaymentMethodDto {

    @EqualsAndHashCode.Include
    private Long id;

    @JsonView({RestaurantView.PaymentMethod.class})
    private String description;

    public PaymentMethodDto(String description) {
        this.description = description;
    }
}
