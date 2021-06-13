package com.study.microservices.studyapplication.domain.service;

import com.study.microservices.studyapplication.api.controller.model.request.restaurant.AddressPutRequest;
import com.study.microservices.studyapplication.api.controller.model.request.restaurant.AddressRequest;
import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantPutRequest;
import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantRequest;
import com.study.microservices.studyapplication.api.controller.model.response.restaurant.PaymentMethodResponse;
import com.study.microservices.studyapplication.api.controller.model.response.restaurant.RestaurantResponse;
import com.study.microservices.studyapplication.core.jmapper.KitchenConverter;
import com.study.microservices.studyapplication.core.jmapper.RestaurantConverter;
import com.study.microservices.studyapplication.domain.dto.PaymentMethodDto;
import com.study.microservices.studyapplication.domain.dto.RestaurantDto;
import com.study.microservices.studyapplication.domain.dto.RestaurantPaymentMethodDto;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import com.study.microservices.studyapplication.domain.model.Address;
import com.study.microservices.studyapplication.domain.model.City;
import com.study.microservices.studyapplication.domain.model.Kitchen;
import com.study.microservices.studyapplication.domain.model.PaymentMethod;
import com.study.microservices.studyapplication.domain.model.Restaurant;
import com.study.microservices.studyapplication.domain.repository.RestaurantRepository;
import com.study.microservices.studyapplication.core.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final KitchenService kitchenService;
    private final CityService cityService;
    private final PaymentMethodService paymentMethodService;
    private final SmartValidator smartValidator;
    private final RestaurantConverter converter;
    private final KitchenConverter kitchenConverter;

    public Page<RestaurantDto> findAll(Pageable pageable) {
        return buildPage(restaurantRepository.findAll(pageable), pageable);
    }

    public RestaurantResponse searchByIdRestaurantResponse(String restaurantCode) {
        return convertEntityToResponse(findByCode(restaurantCode));
    }

    public RestaurantDto searchById(String restaurantCode) {
        return converter.convert(findByCode(restaurantCode));
    }

    @Transactional
    public RestaurantResponse save(RestaurantRequest request) {
        kitchenService.searchById(request.getKitchenId());
        cityService.searchById(request.getAddress().getCityId());
        return convertEntityToResponse(restaurantRepository.save(convertRequestToEntity(request)));
    }

    @Transactional
    public RestaurantResponse update(RestaurantPutRequest request, String restaurantCode) {
        Restaurant restaurantActual = this.findByCode(restaurantCode);

        validateKitchenPut(request.getKitchenId(), restaurantActual);
        validateAddressPut(request.getAddress(), restaurantActual);

        updateObjectRestaurant(request, restaurantActual);
        return convertEntityToResponse(restaurantRepository.save(restaurantActual));
    }

    @Transactional
    public RestaurantResponse partialUpdate(Map<String, Object> fields, String restaurantCode) {
        fields.entrySet()
                .stream()
                .filter(field -> "kitchen".equals(field.getKey()))
                .findFirst()
                .ifPresent(field -> {
                    Long kitchenId = Long.valueOf(((LinkedHashMap) field.getValue()).get("id").toString());
                    kitchenService.searchById(kitchenId);
                });

        Restaurant restaurantActual = this.findByCode(restaurantCode);
        EntityMergeService.getInstance(Restaurant.class).mergeObject(restaurantActual, fields);
        validatePartialUpdate(converter.convert(restaurantActual));
        return convertEntityToResponse(restaurantRepository.save(restaurantActual));
    }

    public List<RestaurantResponse> search(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal) {
        return buildListRestaurantResponse(restaurantRepository.searchByFreeShippingAndName(name));
    }

    public Optional<List<RestaurantDto>> searchByKitchen(Long kitchenId) {
        return of(converter.convertListRestaurantDto(restaurantRepository.findAllByKitchen(kitchenId)));
    }

    public Optional<List<RestaurantDto>> searchByPaymentMethod(Long paymentMethodId) {
        return of(converter.convertListRestaurantDto(restaurantRepository
                .findAllByPaymentMethod(paymentMethodId, PageRequest.of(0, 1))));
    }

    @Transactional
    public void activate(String restaurantCode) {
        Restaurant restaurantActual = this.findByCode(restaurantCode);
        restaurantActual.setEnable(TRUE);
    }

    @Transactional
    public void deactivate(String restaurantCode) {
        Restaurant restaurantActual = this.findByCode(restaurantCode);
        restaurantActual.setEnable(FALSE);
    }

    @Transactional
    public void disassociate(String restaurantCode, Long paymentMethodId) {
        Restaurant restaurantActual = this.findByCode(restaurantCode);
        PaymentMethodDto paymentMethodDto = paymentMethodService.searchById(paymentMethodId);
        restaurantActual.getPaymentMethods().remove(new PaymentMethod(paymentMethodDto.getId(), paymentMethodDto.getDescription()));
    }

    @Transactional
    public void associate(String restaurantCode, Long paymentMethodId) {
        Restaurant restaurantActual = this.findByCode(restaurantCode);
        PaymentMethodDto paymentMethodDto = paymentMethodService.searchById(paymentMethodId);
        restaurantActual.getPaymentMethods().add(new PaymentMethod(paymentMethodDto.getId(), paymentMethodDto.getDescription()));
    }

    public PaymentMethodResponse searchPaymentMethodByRestaurantIdPaymentMethodResponse(String restaurantCode) {
        return buildPaymentMethodResponse(restaurantRepository.findAllPaymentMethod(restaurantCode));
    }

    public RestaurantDto searchPaymentMethodByRestaurantId(String restaurantCode) {
        RestaurantDto restaurantDto = new RestaurantDto();
        List<RestaurantPaymentMethodDto> dtos = restaurantRepository.findAllPaymentMethod(restaurantCode);

        if (isNull(dtos) || isEmpty(dtos)) {
            return restaurantDto;
        }

        restaurantDto.setCode(dtos.get(0).getRestaurantCode());
        restaurantDto.setPaymentMethods(dtos.stream()
                .map(dto -> new PaymentMethodDto(dto.getDescription()))
                .collect(toSet()));

        return restaurantDto;
    }

    private void validateKitchenPut(Long kitchenIdRequest, Restaurant restaurantActual) {
        boolean isKitchenChanged = nonNull(kitchenIdRequest) &&
                (isNull(restaurantActual.getKitchen()) || !restaurantActual.getKitchen().getId().equals(kitchenIdRequest));

        if (isKitchenChanged) {
            kitchenService.searchById(kitchenIdRequest);
        }
    }

    private void validateAddressPut(AddressPutRequest request, Restaurant restaurantActual) {
        boolean isRestaurantWithAddress = nonNull(restaurantActual.getAddress());
        boolean isAddressWithCity = isRestaurantWithAddress && nonNull(restaurantActual.getAddress().getCity());
        boolean isCityAddressChanged = nonNull(request) &&
                (!isAddressWithCity || (isAddressWithCity && !restaurantActual.getAddress().getCity().equals(request.getCityId())));

        if (isCityAddressChanged) {
            cityService.searchById(request.getCityId());
        }
    }

    private void validatePartialUpdate(RestaurantDto restaurantActual) {
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(restaurantActual, "restaurant");
        smartValidator.validate(restaurantActual, result);

        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
    }

    private Restaurant findByCode(String restaurantCode) {
        Supplier<UnprocessableEntityException> unprocessableEntitySupplier = ()
                -> new UnprocessableEntityException("Restaurant of code %s not found.", restaurantCode);
        return ofNullable(restaurantRepository.findByCode(restaurantCode)).orElseThrow(unprocessableEntitySupplier);
    }

    private void updateObjectRestaurant(RestaurantPutRequest restaurantUpdated, Restaurant restaurantActual) {
        restaurantActual.setName(nonNull(restaurantUpdated.getName()) ? restaurantUpdated.getName() : restaurantActual.getName());
        restaurantActual.setFreightRate(nonNull(restaurantUpdated.getFreightRate()) ? restaurantUpdated.getFreightRate() : restaurantActual.getFreightRate());
        restaurantActual.setKitchen(nonNull(restaurantUpdated.getKitchenId())
                ? new Kitchen(restaurantUpdated.getKitchenId(), null) : restaurantActual.getKitchen());

        if (nonNull(restaurantUpdated.getAddress())) {
            Address address = new Address();
            address.setZipCode(restaurantUpdated.getAddress().getZipCode());
            address.setCity(new City(restaurantUpdated.getAddress().getCityId()));
            address.setStreet(restaurantUpdated.getAddress().getStreet());
            address.setNumber(restaurantUpdated.getAddress().getNumber());
            address.setNeighborhood(restaurantUpdated.getAddress().getNeighborhood());
            address.setComplement(restaurantUpdated.getAddress().getComplement());
            restaurantActual.setAddress(address);
        }
    }

    private Address buildAddressFromRequest(AddressRequest request) {
        Address address = new Address();

        if (isNull(request)) {
            return address;
        }

        address.setCity(new City());
        address.getCity().setId(request.getCityId());
        address.setComplement(request.getComplement());
        address.setNeighborhood(request.getNeighborhood());
        address.setNumber(request.getNumber());
        address.setStreet(request.getStreet());
        address.setZipCode(request.getZipCode());
        return address;
    }

    private Restaurant convertRequestToEntity(RestaurantRequest request) {
        if (isNull(request)) {
            return null;
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setFreightRate(request.getFreightRate());
        restaurant.setKitchen(new Kitchen());
        restaurant.getKitchen().setId(request.getKitchenId());
        restaurant.setAddress(buildAddressFromRequest(request.getAddress()));
        return restaurant;
    }

    private RestaurantResponse convertEntityToResponse(Restaurant entity) {
        if (isNull(entity)) {
            return new RestaurantResponse();
        }

        RestaurantResponse response = new RestaurantResponse();
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setFreightRate(entity.getFreightRate());
        response.setEnable(entity.getEnable());
        RestaurantDto restaurantDto = converter.convert(entity);
        response.buildKitchen(restaurantDto.getKitchen());
        response.buildAddress(restaurantDto.getAddress());

        return response;
    }

    private List<RestaurantResponse> buildListRestaurantResponse(List<Restaurant> entities) {
        if (isNull(entities) || isEmpty(entities)) {
            return emptyList();
        }

        return entities.stream()
                .map(this::convertEntityToResponse)
                .collect(toList());
    }

    private PaymentMethodResponse buildPaymentMethodResponse(List<RestaurantPaymentMethodDto> dto) {
        if (isNull(dto) || isEmpty(dto)) {
            return new PaymentMethodResponse(emptySet());
        }

        return new PaymentMethodResponse(dto.stream()
                .map(RestaurantPaymentMethodDto::getDescription)
                .collect(toSet()));
    }

    private Page<RestaurantDto> buildPage(Page<Restaurant> restaurants, Pageable pageable) {
        if (isNull(restaurants) || restaurants.isEmpty()) {
            return Page.empty();
        }

        return new PageImpl<RestaurantDto>(converter.convertListRestaurantDto(restaurants.getContent()),
                pageable, restaurants.getTotalElements());
    }
}
