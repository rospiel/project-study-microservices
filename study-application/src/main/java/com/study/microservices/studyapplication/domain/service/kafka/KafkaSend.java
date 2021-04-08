package com.study.microservices.studyapplication.domain.service.kafka;

import com.study.microservices.studyapplication.api.controller.model.request.KitchensRequest;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.service.kafka.serializer.KitchenSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class KafkaSend {

    public static void main(String[] args) {

        Properties proper = new Properties();
        /* Address of the kafka server */
        proper.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        /* Based on a kind of message what type of serializer will be necessary to get de key  */
        proper.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        /* Based on a kind of message what type of serializer will be necessary to get de value  */
        proper.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        /* to close automatically */
        /* Our message use String for the key and value */
        try (KafkaProducer<String, String> producer = new KafkaProducer<String, String>(proper)) {

            ProducerRecord<String, String> record = new ProducerRecord<String, String>("TestJava", "Hii");
            producer.send(record);
            /* Isn't necessary because of the try  producer.close(); */
        }
    }

    public void sendKitchens(KitchensRequest request) {
        if (isNull(request) || isEmpty(request.getKitchens())) {
            return;
        }

        Properties proper = new Properties();
        proper.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        proper.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        proper.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KitchenSerializer.class.getName());

        try (KafkaProducer<String, KitchenDto> producer = new KafkaProducer<String, KitchenDto>(proper)) {

            request.getKitchens().stream().forEach(kitchen -> {
                ProducerRecord<String, KitchenDto> record = new ProducerRecord<String, KitchenDto>("kitchensInclude", kitchen);
                producer.send(record);
            });

        }
    }
}
