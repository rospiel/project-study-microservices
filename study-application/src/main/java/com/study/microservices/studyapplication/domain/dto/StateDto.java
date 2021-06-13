package com.study.microservices.studyapplication.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.microservices.studyapplication.api.controller.model.response.view.restaurant.RestaurantView;
import com.study.microservices.studyapplication.core.validation.Groups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "State", description = "Representation of a state")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class StateDto {

    @ApiModelProperty(value = "Id of a state", example = "1")
    @NotNull(groups = {Groups.IncludeCity.class})
    private Long id;

    @ApiModelProperty(value = "Name of the state", example = "São Paulo")
    @NotBlank
    @JsonView({RestaurantView.Resume.class})
    private String name;
}
