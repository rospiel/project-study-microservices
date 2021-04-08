package com.study.microservices.studyapplication.domain.service.kafka;

import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.service.KitchenService;
import com.study.microservices.studyapplication.domain.service.kafka.serializer.KitchenDeserializer;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.stream.StreamSupport;

import static java.lang.Boolean.FALSE;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class KafkaReceive {

    private final KitchenService kitchenService;

    public static void main(String[] args) {

        Properties proper = new Properties();
        /* Address of the kafka server */
        proper.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        /* Based on a kind of message what type of serializer will be necessary to get de key  */
        proper.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        /* Based on a kind of message what type of serializer will be necessary to get de value  */
        proper.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        /* What group we will participate */
        proper.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        /* Must to consume since the beginning */
        proper.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        /* to close automatically */
        /* We are expecting String in a key and value */
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(proper)) {

            /* The topic name we will consume */
            consumer.subscribe(asList("TestJava"));
            /* Still searching */
            while(true) {
                /* Wait 1 second for new messages */
                ConsumerRecords<String, String> messages = consumer.poll(Duration.ofSeconds(1));
                messages.forEach(message -> System.out.println(message.value()));
                /* Isn't necessary because of the try consumer.close(); */
            }
        }
    }

    public void receiveKitchens() {
        Properties proper = new Properties();
        proper.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        proper.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        proper.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KitchenDeserializer.class.getName());

        proper.put(ConsumerConfig.GROUP_ID_CONFIG, "post-kitchens");
        proper.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (KafkaConsumer<String, KitchenDto> consumer = new KafkaConsumer<>(proper)) {
            consumer.subscribe(asList("kitchensInclude"));
            ConsumerRecords<String, KitchenDto> messages = consumer.poll(Duration.ofSeconds(1));
            List<KitchenDto> kitchens = StreamSupport.stream(messages.spliterator(), FALSE)
                    .map(message -> message.value())
                    .collect(toList());
            kitchenService.saveAll(kitchens);
        }
    }
}
