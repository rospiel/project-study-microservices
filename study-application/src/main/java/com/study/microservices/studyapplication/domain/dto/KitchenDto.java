package com.study.microservices.studyapplication.domain.dto;

import com.study.microservices.studyapplication.core.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KitchenDto {

    @NotNull(groups = {Groups.IncludeRestaurant.class})
    private Long id;

    @NotBlank
    private String name;
}
