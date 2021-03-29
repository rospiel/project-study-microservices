package com.study.microservices.studyapplication.domain.dto;

import com.study.microservices.studyapplication.core.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class KitchenDto {

    @NotNull(groups = {Groups.IncludeRestaurant.class})
    private Long id;

    @NotBlank
    private String name;
}
