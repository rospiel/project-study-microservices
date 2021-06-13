package com.study.microservices.studyapplication.core.openapi.model;

import com.study.microservices.studyapplication.domain.dto.RestaurantDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("RestaurantsPage")
@Setter
@Getter
public class RestaurantsPageModelOpenApi extends DefaultPageModelOpenApi {

    @ApiModelProperty("Representation of a domain")
    private List<RestaurantDto> content;
}
