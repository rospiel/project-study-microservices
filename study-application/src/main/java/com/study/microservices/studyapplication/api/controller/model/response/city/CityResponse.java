package com.study.microservices.studyapplication.api.controller.model.response.city;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import com.study.microservices.studyapplication.api.controller.model.response.view.restaurant.RestaurantView;
import com.study.microservices.studyapplication.core.validation.Groups;
import com.study.microservices.studyapplication.domain.dto.StateDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonFilter("cityFilter")
public class CityResponse {

    private Long id;

    private String name;

    @JsonFilter("cityStateFilter")
    private CityStateResponse state;
}
