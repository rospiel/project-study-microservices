package com.study.microservices.studyapplication.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantPutRequest;
import com.study.microservices.studyapplication.api.controller.model.request.restaurant.RestaurantRequest;
import com.study.microservices.studyapplication.api.controller.model.response.restaurant.PaymentMethodResponse;
import com.study.microservices.studyapplication.api.controller.model.response.restaurant.RestaurantResponse;
import com.study.microservices.studyapplication.api.controller.model.response.view.restaurant.RestaurantView;
import com.study.microservices.studyapplication.domain.dto.RestaurantDto;
import com.study.microservices.studyapplication.domain.service.RestaurantService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Restaurant")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/restaurants", produces = APPLICATION_JSON_VALUE)
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;
    private static final String BY_CODE = "/{restaurantCode}";
    private static final String PAYMENT_METHOD = "/paymentMethods";
    private static final String PAYMENT_METHOD_BY_ID = "/{paymentMethodId}";

    @GetMapping
    @ResponseStatus(OK)
    public Page<RestaurantDto> list(@PageableDefault(size = 1) Pageable pageable, @AuthenticationPrincipal Object o) {
        return restaurantService.findAll(pageable);
    }

    @GetMapping(BY_CODE)
    public ResponseEntity<RestaurantResponse> search(@PathVariable String restaurantCode) {
        return ResponseEntity.ok(restaurantService.searchByIdRestaurantResponse(restaurantCode));
    }

    @JsonView(RestaurantView.Resume.class)
    @GetMapping("/resume" + BY_CODE)
    public ResponseEntity<RestaurantDto> searchToResume(@PathVariable String restaurantCode) {
        return ResponseEntity.ok(restaurantService.searchById(restaurantCode));
    }

    @GetMapping(BY_CODE + PAYMENT_METHOD)
    @ResponseStatus(OK)
    public PaymentMethodResponse searchByRestaurantPaymentMethodResponse(@PathVariable String restaurantCode) {
        return restaurantService.searchPaymentMethodByRestaurantIdPaymentMethodResponse(restaurantCode);
    }

    @JsonView(RestaurantView.PaymentMethod.class)
    @GetMapping("/v2" + BY_CODE + PAYMENT_METHOD)
    @ResponseStatus(OK)
    public RestaurantDto searchByRestaurant(@PathVariable String restaurantCode) {
        return restaurantService.searchPaymentMethodByRestaurantId(restaurantCode);
    }

    @DeleteMapping(BY_CODE + PAYMENT_METHOD + PAYMENT_METHOD_BY_ID)
    @ResponseStatus(NO_CONTENT)
    public void disassociate(@PathVariable String restaurantCode, @PathVariable Long paymentMethodId) {
        restaurantService.disassociate(restaurantCode, paymentMethodId);
    }

    @PutMapping(BY_CODE + PAYMENT_METHOD + PAYMENT_METHOD_BY_ID)
    @ResponseStatus(NO_CONTENT)
    public void associate(@PathVariable String restaurantCode, @PathVariable Long paymentMethodId) {
        restaurantService.associate(restaurantCode, paymentMethodId);
    }

    @GetMapping("/nameAndFreightRateInitialAndFinal")
    public ResponseEntity<List<RestaurantResponse>> search(@RequestParam String restaurantName, @RequestParam BigDecimal freightRateInitial, @RequestParam BigDecimal freightRateFinal) {
        return ResponseEntity.ok(restaurantService.search(restaurantName, freightRateInitial, freightRateFinal));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void include(@RequestBody @Valid RestaurantRequest restaurant) {
        restaurantService.save(restaurant);
    }

    @PutMapping(value = BY_CODE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody @Valid RestaurantPutRequest restaurant, @PathVariable String restaurantCode) {
        restaurantService.update(restaurant, restaurantCode);
    }

    @PatchMapping(value = BY_CODE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void partialUpdate(@RequestBody Map<String, Object> fields, @PathVariable String restaurantCode) {
        restaurantService.partialUpdate(fields, restaurantCode);
    }

    @PutMapping(value = BY_CODE + "/activation", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void active(@PathVariable String restaurantCode) {
        restaurantService.activate(restaurantCode);
    }

    @DeleteMapping(value = BY_CODE + "/deactivation", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void deactivate(@PathVariable String restaurantCode) {
        restaurantService.deactivate(restaurantCode);
    }
}
