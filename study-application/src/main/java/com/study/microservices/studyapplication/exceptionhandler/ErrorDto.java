package com.study.microservices.studyapplication.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL) /* this way we remove every property nullable from response */
@Getter
@Builder /* pattern to construct a new instance */
public class ErrorDto {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    private LocalDateTime dateTime;

}
