package com.study.microservices.studyapplication.domain.service;

import com.study.microservices.studyapplication.core.jmapper.KitchenConverter;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.exception.EntityAlreadyInUseException;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import com.study.microservices.studyapplication.domain.model.Kitchen;
import com.study.microservices.studyapplication.domain.repository.KitchenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Slf4j
public class KitchenService {

    @Autowired
    private KitchenRepository kitchenRepository;

    @Lazy
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private KitchenConverter converter;

    @Transactional
    public KitchenDto save(KitchenDto kitchen) {
        return converter.convert(kitchenRepository.save(converter.convert(kitchen)));
    }

    @Transactional
    public void saveAll(List<KitchenDto> kitchens) {
        kitchenRepository.saveAll(converter.convertListKitchen(kitchens));
    }

    @Transactional
    public Kitchen update(KitchenDto kitchen, Long kitchenId) {
        Kitchen kitchenBase = kitchenRepository.findById(kitchenId).orElseThrow(() -> new EmptyResultDataAccessException(kitchenId.intValue()));
        copyProperties(kitchen, kitchenBase, "id");
        return kitchenRepository.save(kitchenBase);
    }

    @Transactional
    public void delete(Long kitchenId) {
        if(restaurantService.searchByKitchen(kitchenId).get().isEmpty() == FALSE) {
            String MESSAGE_ERROR = "There is/are restaurant(s) associate a kitchen of id.: %s";
            throw new EntityAlreadyInUseException(format(MESSAGE_ERROR, kitchenId));
        }
        kitchenRepository.delete(findById(kitchenId));
    }

    public List<KitchenDto> findAll() {
        return converter.convertListKitchenDto(kitchenRepository.findAll());
    }

    public KitchenDto searchById(Long kitchenId) {
        return converter.convert(findById(kitchenId));
    }

    public List<KitchenDto> findByName(String kitchenName) {
        return converter.convertListKitchenDto(kitchenRepository.findByName(kitchenName).orElseThrow(()
                -> new UnprocessableEntityException(format("Kitchen of name %s not found.", kitchenName))));
    }

    private Kitchen findById(Long kitchenId) {
        return kitchenRepository.findById(kitchenId).orElseThrow(()
                -> new UnprocessableEntityException("Kitchen of id %s not found.", kitchenId));
    }
}
