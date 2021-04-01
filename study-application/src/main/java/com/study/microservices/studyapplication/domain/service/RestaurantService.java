package com.study.microservices.studyapplication.domain.service;

import com.study.microservices.studyapplication.domain.dto.RestaurantDto;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import com.study.microservices.studyapplication.domain.model.Kitchen;
import com.study.microservices.studyapplication.domain.model.Restaurant;
import com.study.microservices.studyapplication.domain.repository.RestaurantRepository;
import com.study.microservices.studyapplication.core.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final KitchenService kitchenService;
    private final SmartValidator smartValidator;

    public List<RestaurantDto> findAll() {
        return convertEntityToDto(restaurantRepository.findAll());
    }

    public RestaurantDto searchById(Long restaurantId) {
        return convertEntityToDto(findById(restaurantId));
    }

    @Transactional
    public RestaurantDto save(RestaurantDto restaurant) {
        kitchenService.searchById(restaurant.getKitchen().getId());
        return convertEntityToDto(restaurantRepository.save(convertDtoToEntity(restaurant)));
    }

    @Transactional
    public RestaurantDto update(RestaurantDto restaurant, Long restaurantId) {
        if (nonNull(restaurant.getKitchen())) {
            kitchenService.searchById(restaurant.getKitchen().getId());
        }

        Restaurant restaurantActual = this.findById(restaurantId);
        updateObjectRestaurant(restaurant, restaurantActual);
        return convertEntityToDto(restaurantRepository.save(restaurantActual));
    }

    @Transactional
    public RestaurantDto partialUpdate(Map<String, Object> fields, Long restaurantId) {
        fields.entrySet()
                .stream()
                .filter(field -> "kitchen".equals(field.getKey()))
                .findFirst()
                .ifPresent(field -> {
                    Long kitchenId = Long.valueOf(((LinkedHashMap) field.getValue()).get("id").toString());
                    kitchenService.searchById(kitchenId);
                });

        Restaurant restaurantActual = this.findById(restaurantId);
        EntityMergeService.getInstance(Restaurant.class).mergeObject(restaurantActual, fields);
        validatePartialUpdate(convertEntityToDto(restaurantActual));
        return convertEntityToDto(restaurantRepository.save(restaurantActual));
    }

    public List<RestaurantDto> search(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal) {
        return convertEntityToDto(restaurantRepository.searchByFreeShippingAndName(name));
    }

    public Optional<List<RestaurantDto>> searchByKitchen(Long kitchenId) {
        return of(convertEntityToDto(restaurantRepository.findAllByKitchen(kitchenId)));
    }

    private void validatePartialUpdate(RestaurantDto restaurantActual) {
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(restaurantActual, "restaurant");
        smartValidator.validate(restaurantActual, result);

        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
    }

    private Restaurant findById(Long restaurantId) {
        Supplier<UnprocessableEntityException> unprocessableEntitySupplier = ()
                -> new UnprocessableEntityException("Restaurant of id %s not found.", restaurantId);
        return ofNullable(restaurantRepository.findById(restaurantId)).orElseThrow(unprocessableEntitySupplier).get();
    }

    private void updateObjectRestaurant(RestaurantDto restaurantUpdated, Restaurant restaurantActual) {
        restaurantActual.setName(nonNull(restaurantUpdated.getName()) ? restaurantUpdated.getName() : restaurantActual.getName());
        restaurantActual.setFreightRate(nonNull(restaurantUpdated.getFreightRate()) ? restaurantUpdated.getFreightRate() : restaurantActual.getFreightRate());
        restaurantActual.setKitchen(nonNull(restaurantUpdated.getKitchen()) && nonNull(restaurantUpdated.getKitchen().getId())
                ? new Kitchen(restaurantUpdated.getKitchen().getId(), null) : restaurantActual.getKitchen());
    }

    private List<RestaurantDto> convertEntityToDto(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::convertEntityToDto)
                .collect(toList());
    }

    private RestaurantDto convertEntityToDto(Restaurant restaurant) {
        return nonNull(restaurant) ?
                new RestaurantDto(restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getFreightRate(),
                        KitchenService.convertEntityToDto(restaurant.getKitchen())) : null;
    }

    public static Restaurant convertDtoToEntity(RestaurantDto restaurantDto) {
        return nonNull(restaurantDto) ?
                new Restaurant(restaurantDto.getName(), restaurantDto.getFreightRate(),
                        KitchenService.convertDtoToEntity(restaurantDto.getKitchen())) : null;

    }
}
