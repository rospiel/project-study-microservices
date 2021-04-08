package com.study.microservices.studyapplication.api.controller;

import com.study.microservices.studyapplication.api.controller.model.KitchensXmlWrapper;
import com.study.microservices.studyapplication.api.controller.model.request.KitchensRequest;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.service.KitchenService;
import com.study.microservices.studyapplication.domain.service.kafka.KafkaReceive;
import com.study.microservices.studyapplication.domain.service.kafka.KafkaSend;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/kitchens", produces = APPLICATION_JSON_VALUE)
@RestController /* @Controller @ResponseBody */
public class KitchenController {

    private static final String BY_ID = "/{kitchenId}";
    private final KitchenService kitchenService;
    private final KafkaSend kafkaSend;
    private final KafkaReceive kafkaReceive;

    @GetMapping
    public List<KitchenDto> list() {
        return kitchenService.findAll();
    }

    @GetMapping("/byName")
    @ResponseStatus(OK)
    public List<KitchenDto> list(@RequestParam String kitchenName) {
        return kitchenService.findByName(kitchenName);
    }

    @GetMapping(produces = APPLICATION_XML_VALUE)
    public KitchensXmlWrapper listXml() {
        return new KitchensXmlWrapper(kitchenService.findAll());
    }

    @GetMapping(BY_ID)
    public ResponseEntity<KitchenDto> search(@PathVariable Long kitchenId) {
        return ResponseEntity.ok(kitchenService.searchById(kitchenId));
    }

    @ResponseStatus(CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void include(@RequestBody @Valid KitchenDto kitchen) {
        kitchenService.save(kitchen);
    }

    @ResponseStatus(CREATED)
    @PostMapping(path = "/sendKitchensKafka", consumes = APPLICATION_JSON_VALUE)
    public void include(@RequestBody @Valid KitchensRequest request) {
        kafkaSend.sendKitchens(request);
    }

    @ResponseStatus(CREATED)
    @PostMapping(path = "/consumeKitchensKafka", consumes = APPLICATION_JSON_VALUE)
    public void include() {
        kafkaReceive.receiveKitchens();
    }

    @PutMapping(value = BY_ID, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody @Valid KitchenDto kitchen, @PathVariable Long kitchenId) {
        kitchenService.update(kitchen, kitchenId);
    }

    @DeleteMapping(BY_ID)
    @ResponseStatus(NO_CONTENT) /* Just in case of success*/
    public void delete(@PathVariable long kitchenId) {
        kitchenService.delete(kitchenId);
    }

}
