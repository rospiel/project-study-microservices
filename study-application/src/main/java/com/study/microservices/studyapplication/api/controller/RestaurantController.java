package com.study.microservices.studyapplication.api.controller;

import com.study.microservices.studyapplication.domain.dto.RestaurantDto;
import com.study.microservices.studyapplication.domain.model.Restaurant;
import com.study.microservices.studyapplication.domain.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/restaurants", produces = APPLICATION_JSON_VALUE)
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;
    private static final String BY_ID = "/{restaurantId}";

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> list() {
        return ResponseEntity.ok(restaurantService.findAll());
    }

    @GetMapping(BY_ID)
    public ResponseEntity<RestaurantDto> search(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.searchById(restaurantId));
    }

    @GetMapping("/nameAndFreightRateInitialAndFinal")
    public ResponseEntity<List<RestaurantDto>> search(@RequestParam String restaurantName, @RequestParam BigDecimal freightRateInitial, @RequestParam BigDecimal freightRateFinal) {
        return ResponseEntity.ok(restaurantService.search(restaurantName, freightRateInitial, freightRateFinal));
    }

    @GetMapping("/findFirst")
    public ResponseEntity<RestaurantDto> searchFirst() {
        return ResponseEntity.ok(restaurantService.searchFirst());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void include(@RequestBody Restaurant restaurant) {
        restaurantService.save(restaurant);
    }

    @PutMapping(value = BY_ID, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable Long restaurantId) {
        restaurantService.update(restaurant, restaurantId);
    }

    @PatchMapping(value = BY_ID, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void partialUpdate(@RequestBody Map<String, Object> fields, @PathVariable Long restaurantId) {
        restaurantService.partialUpdate(fields, restaurantId);
    }
}
