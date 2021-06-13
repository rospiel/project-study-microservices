package com.study.microservices.studyapplication.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.time.LocalDateTime.now;

@ApiModel(value = "Error", description = "Class to represent system errors")
@JsonInclude(NON_NULL) /* this way we remove every property nullable from response */
@Getter
@Builder /* pattern to construct a new instance */
public class ErrorDto {

    @ApiModelProperty(value = "Http code", example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(value = "Page of a error", example = "https://studyapplication.com/invalidated_property", position = 5)
    private String type;

    @ApiModelProperty(value = "Title of the error", example = "Invalidated property", position = 10)
    private String title;

    @ApiModelProperty(value = "Details to support developers", example = "Validation failed for argument [0] in public void com.study...", position = 15)
    private String detail;

    @ApiModelProperty(value = "List of object and/or fields with errors", position = 20)
    /* To use in bean validations  */
    private List<FieldErrorDto> fields;

    @ApiModelProperty(value = "Message to show to the user", position = 25)
    private String userMessage;

    @ApiModelProperty(value = "Occurrence date", example = "2021-05-02T16:35:25.333646", position = 30)
    @Builder.Default
    private LocalDateTime dateTime = now();

}
