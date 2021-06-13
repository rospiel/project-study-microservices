package com.study.microservices.studyapplication.api.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.study.microservices.studyapplication.api.controller.model.response.city.CityResponse;
import com.study.microservices.studyapplication.api.controller.swagger.CityControllerSwagger;
import com.study.microservices.studyapplication.core.security.Security;
import com.study.microservices.studyapplication.domain.dto.CityDto;
import com.study.microservices.studyapplication.domain.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cities", produces = APPLICATION_JSON_VALUE)
public class CityController implements CityControllerSwagger {

    private final CityService cityService;
    private final String BY_ID = "/{cityId}";

    @Security.Cities.CanSearch
    @GetMapping
    public ResponseEntity<List<CityDto>> list() {
        List<CityDto> cities = cityService.findAll();

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(cities);
    }

    @PostMapping("/test")
    public ResponseEntity<?> list2() {
        return ResponseEntity.unprocessableEntity().build();
    }

    @GetMapping(BY_ID)
    public ResponseEntity<CityDto> search(@PathVariable Long cityId) {
        return ResponseEntity.ok(cityService.searchById(cityId));
    }

    @GetMapping(BY_ID + "/filter")
    @ResponseStatus(OK)
    public MappingJacksonValue searchFilter(@PathVariable Long cityId,
                                            @RequestParam(required = false) String fields) {
        return buildSearchFilter(cityService.searchByIdFilterResult(cityId), fields);
    }

    private MappingJacksonValue buildSearchFilter(CityResponse response, String fields) {
        /* Filters we are using to this response */
        List<String> filterNames = List.of("cityFilter", "cityStateFilter");

        /* Here we need to pass the class that have annotation @JsonFilter */
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(response);
        /* Class the receive the name of the filter also the fields a consumer informed in the request  */
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();

        if (isBlank(fields)) {
            /* for every filter we ask to show every field[serializeAll] since anyone was informed in the request */
            filterNames.stream()
                    .forEach(filter -> filterProvider.addFilter(filter, SimpleBeanPropertyFilter.serializeAll()));

            /* after config we pass to the class that have the response */
            mappingJacksonValue.setFilters(filterProvider);
            return mappingJacksonValue;
        }

        String[] arrayFields = fields.trim().split(",");
        /* for every filter we ask to show just the field we find[filterOutAllExcept] */
        filterNames.stream()
                .forEach(filter -> filterProvider.addFilter(filter, SimpleBeanPropertyFilter.filterOutAllExcept(arrayFields)));

        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void include(@RequestBody @Valid CityDto cityDto) {
        cityService.save(cityDto);
    }

    @PutMapping(BY_ID)
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody @Valid CityDto cityDto,
                       @PathVariable Long cityId) {
        cityService.update(cityDto, cityId);
    }

    @DeleteMapping(BY_ID)
    @ResponseStatus(OK)
    public void delete(@PathVariable Long cityId) {
        cityService.delete(cityId);
    }
}
