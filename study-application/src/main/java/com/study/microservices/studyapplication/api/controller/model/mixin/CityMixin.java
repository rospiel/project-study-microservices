package com.study.microservices.studyapplication.api.controller.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.microservices.studyapplication.core.validation.Groups;
import com.study.microservices.studyapplication.domain.dto.StateDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class CityMixin {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    @JsonProperty("state")
    private StateDto state;
}
