package com.study.microservices.studyapplication.api.controller.model.response.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.microservices.studyapplication.domain.dto.AddressDto;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantResponse {

    @EqualsAndHashCode.Include
    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("freightRate")
    private BigDecimal freightRate;

    @JsonProperty("kitchen")
    private Kitchen kitchen;

    @JsonProperty("enable")
    private Boolean enable;

    @JsonProperty("address")
    private Address address;

    public void buildKitchen(KitchenDto dto) {
        if (isNull(dto)) {
            this.kitchen = new Kitchen();
            return;
        }

        this.kitchen = new Kitchen(dto.getId(), dto.getName());
    }

    public void buildAddress(AddressDto dto) {
        if (isNull(dto)) {
            this.address = new Address();
            return;
        }

        String stateName = nonNull(dto.getCity()) && nonNull(dto.getCity().getState()) ? dto.getCity().getState().getName() : EMPTY;
        String cityName = nonNull(dto.getCity()) ? dto.getCity().getName() : EMPTY;
        this.address = new Address(dto.getZipCode(), dto.getStreet(), dto.getNumber(), dto.getComplement(),
                dto.getNeighborhood(), stateName, cityName);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private class Kitchen {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private class Address {
        @JsonProperty("zipCode")
        private String zipCode;

        @JsonProperty("street")
        private String street;

        @JsonProperty("number")
        private String number;

        @JsonProperty("complement")
        private String complement;

        @JsonProperty("neighborhood")
        private String neighborhood;

        @JsonProperty("stateName")
        private String stateName;

        @JsonProperty("cityName")
        private String cityName;
    }

}
