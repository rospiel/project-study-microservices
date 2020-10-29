package com.study.microservices.studyapplication.api.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.model.Kitchen;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@JacksonXmlRootElement(localName = "kitchens")
@Data
public class KitchensXmlWrapper {

    @JacksonXmlProperty(localName = "kitchen")
    @JacksonXmlElementWrapper(useWrapping = false)
    @NonNull /* create a constructor with lombok */
    private List<KitchenDto> kitchens;
}
