package com.study.microservices.studyapplication.domain.service;

import com.study.microservices.studyapplication.domain.dto.CityDto;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import com.study.microservices.studyapplication.domain.model.City;
import com.study.microservices.studyapplication.domain.model.State;
import com.study.microservices.studyapplication.domain.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;
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

    public CityDto save(CityDto cityDto) {
        stateService.findById(cityDto.getState().getId());
        return convertEntityToDto(cityRepository.save(this.convertDtoToEntity(cityDto)));
    }

    public CityDto update(CityDto cityDto, Long cityId) {
        if (nonNull(cityDto.getState())) {
            stateService.findById(cityDto.getState().getId());
        }

        City cityActual = this.findById(cityId);
        this.updateObjectCity(cityDto, cityActual);
        return convertEntityToDto(cityRepository.save(cityActual));
    }

    public void delete(Long cityId) {
        cityRepository.deleteById(cityId);
    }

    private void updateObjectCity(CityDto cityUpdate, City cityActual) {
        cityActual.setName(nonNull(cityUpdate.getName()) ? cityUpdate.getName() : cityActual.getName());
        cityActual.setState(nonNull(cityUpdate.getState()) && nonNull(cityUpdate.getState().getId())
                ? new State(cityUpdate.getState().getId(), null) : cityActual.getState());
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
                new City(cityDto.getName(), StateService.convertDtoToEntity(cityDto.getState())) :
                null;
    }

    private City findById(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() ->
                new UnprocessableEntityException(format("City of name %s not found.", cityId)));
    }
}
