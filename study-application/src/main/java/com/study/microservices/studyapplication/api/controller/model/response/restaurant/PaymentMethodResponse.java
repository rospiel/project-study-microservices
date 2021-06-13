package com.study.microservices.studyapplication.api.controller.model.response.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodResponse {

    @JsonProperty("paymentMethods")
    private Set<String> paymentMethods;
}
