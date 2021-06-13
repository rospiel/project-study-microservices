package com.study.microservices.studyapplication.api.controller.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentMethodResponse {

    @EqualsAndHashCode.Include
    @JsonProperty("id")
    private Long id;

    @JsonProperty("description")
    private String description;
}
