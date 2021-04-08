package com.study.microservices.studyapplication.domain.service.kafka.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import org.apache.kafka.common.serialization.Serializer;

import static java.util.Objects.isNull;

public class KitchenSerializer implements Serializer<KitchenDto> {


    @Override
    public byte[] serialize(String topic, KitchenDto kitchenDto) {
        return isNull(kitchenDto) ? null : buildBytes(kitchenDto);
    }

    private byte[] buildBytes(KitchenDto kitchenDto) {
        try {
            return new ObjectMapper().writeValueAsBytes(kitchenDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
