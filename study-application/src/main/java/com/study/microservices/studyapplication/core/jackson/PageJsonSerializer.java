package com.study.microservices.studyapplication.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/* allow us to expose a new serializer without put manually on ObjectMapper */
@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(Page<?> page, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        /* start to write */
        jsonGenerator.writeStartObject();

        /* create a new page just with this properties that we declare removing any other not informed */
        jsonGenerator.writeObjectField("content", page.getContent());
        jsonGenerator.writeObjectField("size", page.getSize());
        jsonGenerator.writeObjectField("totalElements", page.getTotalElements());
        jsonGenerator.writeObjectField("totalPages", page.getTotalPages());
        jsonGenerator.writeObjectField("number", page.getNumber());

        /* end */
        jsonGenerator.writeEndObject();
    }


}
