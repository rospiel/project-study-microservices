package com.study.microservices.studyapplication.domain.service;

import com.study.microservices.studyapplication.api.controller.model.request.PaymentMethodRequest;
import com.study.microservices.studyapplication.api.controller.model.request.PaymentMethodUpdateRequest;
import com.study.microservices.studyapplication.api.controller.model.response.PaymentMethodResponse;
import com.study.microservices.studyapplication.core.jmapper.PaymentMethodConverter;
import com.study.microservices.studyapplication.domain.dto.PaymentMethodDto;
import com.study.microservices.studyapplication.domain.exception.EntityAlreadyInUseException;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import com.study.microservices.studyapplication.domain.model.PaymentMethod;
import com.study.microservices.studyapplication.domain.repository.PaymentMethodRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository repository;

    @Lazy
    @Autowired
    private RestaurantService restaurantService;
    private final PaymentMethodConverter converter;

    @Transactional
    public PaymentMethodResponse save(PaymentMethodRequest request) {
        return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }

    @Transactional
    public PaymentMethodResponse update(PaymentMethodUpdateRequest request, Long paymentMethodId) {
        PaymentMethod paymentMethod = this.findById(paymentMethodId);
        updateObjectPaymentMethod(request, paymentMethod);
        return convertEntityToResponse(paymentMethod);
    }

    @Transactional
    public void delete(Long paymentMethodId) {
        if(restaurantService.searchByPaymentMethod(paymentMethodId).get().isEmpty() == FALSE) {
            String MESSAGE_ERROR = "There is/are restaurant(s) associate a payment method of id.: %s";
            throw new EntityAlreadyInUseException(format(MESSAGE_ERROR, paymentMethodId));
        }
        repository.delete(findById(paymentMethodId));
    }

    public List<PaymentMethodResponse> findAll() {
        return buildListRestaurantResponse(repository.findAll());
    }

    public PaymentMethodResponse search(Long paymentMethodId) {
        return convertEntityToResponse(findById(paymentMethodId));
    }

    public PaymentMethodDto searchById(Long paymentMethodId) {
        return converter.convert(findById(paymentMethodId));
    }

    private void updateObjectPaymentMethod(PaymentMethodUpdateRequest request, PaymentMethod entity) {
        entity.setDescription(nonNull(request.getDescription()) ? request.getDescription() : entity.getDescription());
    }

    private PaymentMethod findById(Long paymentMethodId) {
        return repository.findById(paymentMethodId).orElseThrow(()
                -> new UnprocessableEntityException("PaymentMethod of id %s not found.", paymentMethodId));
    }

    private PaymentMethod convertRequestToEntity(PaymentMethodRequest request) {
        if (isNull(request)) {
            return null;
        }

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setDescription(request.getDescription());
        return paymentMethod;
    }

    private PaymentMethodResponse convertEntityToResponse(PaymentMethod entity) {
        if (isNull(entity)) {
            return null;
        }

        PaymentMethodResponse response = new PaymentMethodResponse();
        response.setId(entity.getId());
        response.setDescription(entity.getDescription());
        
        return response;
    }

    private List<PaymentMethodResponse> buildListRestaurantResponse(List<PaymentMethod> entities) {
        if (isNull(entities) || isEmpty(entities)) {
            return emptyList();
        }

        return entities.stream()
                .map(this::convertEntityToResponse)
                .collect(toList());
    }
}
