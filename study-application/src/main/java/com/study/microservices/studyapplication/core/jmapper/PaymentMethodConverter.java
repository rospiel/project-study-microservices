package com.study.microservices.studyapplication.core.jmapper;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import com.study.microservices.studyapplication.domain.dto.PaymentMethodDto;
import com.study.microservices.studyapplication.domain.model.PaymentMethod;
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
public class PaymentMethodConverter {

    private final JMapperAPI jMapperAPI;
    private JMapper paymentMethod;
    private JMapper dto;

    @PostConstruct
    private void setUp() {
        configForDto();
        paymentMethod = new JMapper(PaymentMethodDto.class, PaymentMethod.class, jMapperAPI);
        configForEntity();
        dto = new JMapper(PaymentMethod.class, PaymentMethodDto.class, jMapperAPI);
    }

    private void configForEntity() {
        jMapperAPI.add(mappedClass(PaymentMethod.class).add(global()));
    }

    private void configForDto() {
        jMapperAPI.add(mappedClass(PaymentMethodDto.class).add(global()));
    }

    public PaymentMethod convert(PaymentMethodDto dto) {
        return isNull(dto) ? null : (PaymentMethod) this.dto.getDestination(dto);
    }

    public List<PaymentMethod> convertListKitchen(List<PaymentMethodDto> dtos) {
        return isNull(dtos) || isEmpty(dtos) ? emptyList() :
                dtos.stream().map(this::convert).collect(toList());
    }

    public PaymentMethodDto convert(PaymentMethod entity) {
        return isNull(entity) ? null : (PaymentMethodDto) this.paymentMethod.getDestination(entity);
    }

    public List<PaymentMethodDto> convertListKitchenDto(List<PaymentMethod> entities) {
        return isNull(entities) || isEmpty(entities) ? emptyList() :
                entities.stream().map(this::convert).collect(toList());
    }
}
