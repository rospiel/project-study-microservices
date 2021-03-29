package com.study.microservices.studyapplication.api.controller;

import com.study.microservices.studyapplication.domain.dto.CityDto;
import com.study.microservices.studyapplication.domain.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cities", produces = APPLICATION_JSON_VALUE)
public class CityController {

    private final CityService cityService;
    private final String BY_ID = "/{cityId}";

    @GetMapping
    public ResponseEntity<List<CityDto>> list() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping(BY_ID)
    public ResponseEntity<CityDto> search(@PathVariable Long cityId) {
        return ResponseEntity.ok(cityService.searchById(cityId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void include(@RequestBody @Valid CityDto cityDto) {
        cityService.save(cityDto);
    }

    @PutMapping(BY_ID)
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody @Valid CityDto cityDto, @PathVariable Long cityId) {
        cityService.update(cityDto, cityId);
    }

    @DeleteMapping(BY_ID)
    @ResponseStatus(OK)
    public void delete(@PathVariable Long cityId) {
        cityService.delete(cityId);
    }
}
