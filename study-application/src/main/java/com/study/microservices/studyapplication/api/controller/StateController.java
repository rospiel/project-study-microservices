package com.study.microservices.studyapplication.api.controller;

import com.study.microservices.studyapplication.domain.dto.StateDto;
import com.study.microservices.studyapplication.domain.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/states", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
@RestController
public class StateController {

    private final StateService stateService;
    private static final String BY_ID = "/{stateId}";

    @GetMapping
    @ResponseStatus(OK)
    public List<StateDto> list() {
        return stateService.findAll();
    }

    @GetMapping(BY_ID)
    @ResponseStatus(OK)
    public StateDto search(@PathVariable Long stateId) {
        return stateService.searchById(stateId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void include(@RequestBody @Valid StateDto stateDto) {
        stateService.save(stateDto);
    }

    @PutMapping(BY_ID)
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody @Valid StateDto stateDto, @PathVariable Long stateId) {
        stateService.update(stateDto, stateId);
    }

    @DeleteMapping(BY_ID)
    @ResponseStatus(OK)
    public void delete(@PathVariable Long stateId) {
        stateService.delete(stateId);
    }

}
