package com.study.microservices.studyapplication.core.jmapper;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.model.Kitchen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.googlecode.jmapper.api.JMapperAPI.global;
import static com.googlecode.jmapper.api.JMapperAPI.mappedClass;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class KitchenConverter {

    private final JMapperAPI jMapperAPI;
    private JMapper kitchen;
    private JMapper dto;

    @PostConstruct
    private void setUp() {
        configForDto();
        kitchen = new JMapper(KitchenDto.class, Kitchen.class, jMapperAPI);
        configForEntity();
        dto = new JMapper(Kitchen.class, KitchenDto.class, jMapperAPI);
    }

    private void configForEntity() {
        jMapperAPI.add(mappedClass(KitchenDto.class).add(global()));
    }

    private void configForDto() {
        jMapperAPI.add(mappedClass(KitchenDto.class).add(global()));
    }

    public Kitchen convert(KitchenDto dto) {
        return isNull(dto) ? null : (Kitchen) this.dto.getDestination(dto);
    }

    public List<Kitchen> convertListKitchen(List<KitchenDto> dtos) {
        return isNull(dtos) || isEmpty(dtos) ? emptyList() :
                dtos.stream().map(this::convert).collect(toList());
    }

    public KitchenDto convert(Kitchen kitchen) {
        return isNull(kitchen) ? null : (KitchenDto) this.kitchen.getDestination(kitchen);
    }

    public List<KitchenDto> convertListKitchenDto(List<Kitchen> kitchens) {
        return isNull(kitchens) || isEmpty(kitchens) ? emptyList() :
                kitchens.stream().map(this::convert).collect(toList());
    }
}
