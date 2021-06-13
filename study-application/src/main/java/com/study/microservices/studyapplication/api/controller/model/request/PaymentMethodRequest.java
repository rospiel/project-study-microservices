package com.study.microservices.studyapplication.api.controller.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentMethodRequest {

    @JsonProperty("description")
    @NotBlank
    private String description;
}
