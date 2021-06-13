package com.study.microservices.studyapplication.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.googlecode.jmapper.annotations.JGlobalMap;
import com.study.microservices.studyapplication.api.controller.model.response.view.restaurant.RestaurantView;
import com.study.microservices.studyapplication.core.validation.Groups;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KitchenDto {

    @NotNull(groups = {Groups.IncludeRestaurant.class})
    private Long id;

    @NotBlank
    @JsonView({RestaurantView.Resume.class})
    private String name;
}
