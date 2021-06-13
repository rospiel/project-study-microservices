package com.study.microservices.studyapplication.api.controller;

import com.study.microservices.studyapplication.api.controller.model.request.PaymentMethodRequest;
import com.study.microservices.studyapplication.api.controller.model.request.PaymentMethodUpdateRequest;
import com.study.microservices.studyapplication.api.controller.model.response.PaymentMethodResponse;
import com.study.microservices.studyapplication.domain.service.PaymentMethodService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RequestMapping(value = "/paymentMethods")
@RestController
public class PaymentMethodController {

    private final PaymentMethodService service;
    private static final String BY_ID = "/{paymentMethodId}";

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public List<PaymentMethodResponse> list() {
        return service.findAll();
    }

    @GetMapping(value = BY_ID, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public PaymentMethodResponse searchById(@PathVariable Long paymentMethodId) {
        return service.search(paymentMethodId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void include(@RequestBody @Valid PaymentMethodRequest request) {
        service.save(request);
    }

    @PutMapping(value = BY_ID, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody @Valid PaymentMethodUpdateRequest request, @PathVariable Long paymentMethodId) {
        service.update(request, paymentMethodId);
    }

    @DeleteMapping(BY_ID)
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long paymentMethodId) {
        service.delete(paymentMethodId);
    }
}
