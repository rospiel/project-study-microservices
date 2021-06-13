package com.study.microservices.studyapplication.api.controller.model.request.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    @JsonProperty("zipCode")
    @NotBlank
    private String zipCode;

    @JsonProperty("street")
    @NotBlank
    private String street;

    @JsonProperty("number")
    @NotBlank
    private String number;

    @JsonProperty("complement")
    @NotBlank
    private String complement;

    @JsonProperty("neighborhood")
    @NotBlank
    private String neighborhood;

    @JsonProperty("cityId")
    @NotNull
    private Long cityId;

}
