package com.study.microservices.studyapplication.domain.service;

import com.study.microservices.studyapplication.domain.dto.StateDto;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import com.study.microservices.studyapplication.domain.model.State;
import com.study.microservices.studyapplication.domain.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StateService {


    private final StateRepository stateRepository;

    public List<StateDto> findAll() {
        return convertEntityToDto(stateRepository.findAll());
    }

    public StateDto searchById(Long stateId) {
        State state = findById(stateId);
        return new StateDto(state.getId(), state.getName());
    }

    public StateDto save(StateDto stateDto) {
        return convertEntityToDto(stateRepository.save(convertDtoToEntity(stateDto)));
    }

    public StateDto update(StateDto stateDto, Long stateId) {
        State stateActual = findById(stateId);
        updateObjectState(stateDto, stateActual);
        return convertEntityToDto(stateRepository.save(stateActual));
    }

    public void delete(Long stateId) {
        stateRepository.delete(findById(stateId));
    }

    public static List<StateDto> convertEntityToDto(List<State> states) {
        return isEmpty(states) ?
                null : states.stream().map(StateService::convertEntityToDto).collect(toList());
    }

    public static StateDto convertEntityToDto(State state) {
        return nonNull(state) ?
                new StateDto(state.getId(), state.getName()) : null;
    }

    public static State convertDtoToEntity(StateDto stateDto) {
        return nonNull(stateDto) ?
                new State(stateDto.getId(), stateDto.getName()) : null;
    }

    private void updateObjectState(StateDto stateUpdated, State stateActual) {
        if (isNull(stateUpdated) || isNull(stateActual)) {
            return;
        }

        stateActual.setName(nonNull(stateUpdated.getName()) ?
                stateUpdated.getName() : stateActual.getName());
    }

    public State findById(Long stateId) {
        return stateRepository.findById(stateId).orElseThrow(()
                -> new UnprocessableEntityException(format("State of id %s not found.", stateId)));
    }
}
