package com.study.microservices.studyapplication.exceptionhandler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "FieldError", description = "Represents objects or fields with errors")
@Getter
@Builder
public class FieldErrorDto {

    @ApiModelProperty(value = "Name of the field or object", example = "state.id", position = 1)
    private String name;

    @ApiModelProperty(value = "Message about what problem the field or object have", example = "state.id is mandatory", position = 5)
    private String userMessage;
}
