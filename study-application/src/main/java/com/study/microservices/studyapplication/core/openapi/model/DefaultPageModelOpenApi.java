package com.study.microservices.studyapplication.core.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DefaultPageModelOpenApi {

    @ApiModelProperty(value = "Quantity of elements in page", example = "5")
    private Long size;

    @ApiModelProperty(value = "Total of elements per page", example = "25")
    private Long totalElements;

    @ApiModelProperty(value = "Total of pages", example = "3")
    private Long totalPages;

    @ApiModelProperty(value = "Number of the page (start in ZERO)", example = "0")
    private Long number;
}
