package com.study.microservices.studyapplication.api.controller.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KitchensRequest {

    @JsonProperty("kitchens")
    @Valid
    private List<KitchenDto> kitchens;

}
