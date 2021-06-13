package com.study.microservices.studyapplication.api.controller.model.response.city;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.microservices.studyapplication.api.controller.model.response.view.restaurant.RestaurantView;
import com.study.microservices.studyapplication.core.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityStateResponse {

    private Long id;

    private String name;
}
