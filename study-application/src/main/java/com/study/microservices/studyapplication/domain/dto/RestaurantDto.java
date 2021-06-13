package com.study.microservices.studyapplication.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.microservices.studyapplication.api.controller.model.response.view.restaurant.RestaurantView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantDto {

    @EqualsAndHashCode.Include
    private Long id;

    @JsonView({RestaurantView.Resume.class, RestaurantView.PaymentMethod.class})
    private String code;

    @JsonView({RestaurantView.Resume.class})
    private String name;

    @JsonView({RestaurantView.Resume.class})
    private BigDecimal freightRate;

    @JsonView({RestaurantView.Resume.class})
    private KitchenDto kitchen;

    @JsonView({RestaurantView.PaymentMethod.class})
    private Set<PaymentMethodDto> paymentMethods;

    @JsonView({RestaurantView.Resume.class})
    private AddressDto address;

    @JsonView({RestaurantView.Resume.class})
    private Boolean enable;

    private OffsetDateTime dateCreate;

    private OffsetDateTime dateUpdate;

}
