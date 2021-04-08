package com.study.microservices.studyapplication.domain.service.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

import static java.util.Objects.isNull;

public class KitchenDeserializer implements Deserializer<KitchenDto> {

    @Override
    public KitchenDto deserialize(String topic, byte[] bytes) {
        return isNull(bytes) ? null : buildKitchen(bytes);
    }

    private KitchenDto buildKitchen(byte[] bytes) {
        try {
            return new ObjectMapper().readValue(bytes, KitchenDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
