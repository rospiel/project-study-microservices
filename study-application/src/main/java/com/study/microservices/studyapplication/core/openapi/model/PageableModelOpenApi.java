package com.study.microservices.studyapplication.core.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("Pageable")
@Setter
@Getter
public class PageableModelOpenApi {

    @ApiModelProperty(value = "Number of the page (start in ZERO)", example = "0")
    private int page;

    @ApiModelProperty(value = "Quantity of elements in page", example = "5")
    private int size;

    @ApiModelProperty(value = "Ordination of values", example = "name,asc,id,desc")
    private List<String> sort;
}
