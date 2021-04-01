package com.study.microservices.studyapplication.domain.service;

import com.study.microservices.studyapplication.domain.dto.CityDto;
import com.study.microservices.studyapplication.domain.exception.EntityAlreadyInUseException;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import com.study.microservices.studyapplication.domain.model.City;
import com.study.microservices.studyapplication.domain.model.State;
import com.study.microservices.studyapplication.domain.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CityService {

    private final CityRepository cityRepository;
    private final StateService stateService;

    public List<CityDto> findAll() {
        return this.convertEntityToDto(cityRepository.findAll());
    }

    public CityDto searchById(Long cityId) {
        return convertEntityToDto(findById(cityId));
    }

    @Transactional
    public CityDto save(CityDto cityDto) {
        stateService.findById(cityDto.getState().getId());
        return convertEntityToDto(cityRepository.save(this.convertDtoToEntity(cityDto)));
    }

    @Transactional
    public CityDto update(CityDto cityDto, Long cityId) {
        if (nonNull(cityDto.getState()) && nonNull(cityDto.getState().getId())) {
            stateService.findById(cityDto.getState().getId());
        }

        City cityActual = this.findById(cityId);
        this.updateObjectCity(cityDto, cityActual);
        return convertEntityToDto(cityRepository.save(cityActual));
    }

    @Transactional
    public void delete(Long cityId) {
        City city = this.findById(cityId);
        if (nonNull(city.getState())) {
            String MESSAGE_ERROR = "There is a state associate to the city of id.: %s";
            throw new EntityAlreadyInUseException(MESSAGE_ERROR, city.getState().getId());
        }

        cityRepository.deleteById(cityId);
    }

    private void updateObjectCity(CityDto cityUpdate, City cityActual) {
        cityActual.setName(nonNull(cityUpdate.getName()) ? cityUpdate.getName() : cityActual.getName());

        if (nonNull(cityUpdate.getState()) && nonNull(cityUpdate.getState().getId())) {
            cityActual.setState(new State(cityUpdate.getState().getId(), null));
            return;
        }

        cityActual.setState(nonNull(cityUpdate.getState()) && isNull(cityUpdate.getState().getId())
                ? null : cityActual.getState());
    }

    private List<CityDto> convertEntityToDto(List<City> cities) {
        return cities.stream()
                .map(this::convertEntityToDto)
                .collect(toList());
    }

    private CityDto convertEntityToDto(City city) {
        return nonNull(city) ?
                new CityDto(city.getId(), city.getName(), StateService.convertEntityToDto(city.getState())) :
                null;
    }

    private City convertDtoToEntity(CityDto cityDto) {
        return nonNull(cityDto) ?
                new City(cityDto.getId(), cityDto.getName(), StateService.convertDtoToEntity(cityDto.getState())) :
                null;
    }

    private City findById(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() ->
                new UnprocessableEntityException("City of name %s not found.", cityId));
    }
}
