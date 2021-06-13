package com.study.microservices.studyapplication.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.microservices.studyapplication.api.controller.model.response.view.restaurant.RestaurantView;
import com.study.microservices.studyapplication.core.validation.Groups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@ApiModel(value = "City", description = "Representation of a city")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class CityDto {

    @ApiModelProperty(value = "Id of the city", example = "1")
    private Long id;

    @ApiModelProperty(value = "Name of the city", example = "Osasco", allowableValues = "true,false")
    @NotBlank
    @JsonView({RestaurantView.Resume.class})
    private String name;

    @Valid
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.IncludeCity.class)
    @JsonView({RestaurantView.Resume.class})
    private StateDto state;
}
