package com.study.microservices.studyapplication.service;

import com.study.microservices.studyapplication.domain.dto.CityDto;
import com.study.microservices.studyapplication.domain.dto.StateDto;
import com.study.microservices.studyapplication.domain.model.City;
import com.study.microservices.studyapplication.domain.model.State;
import com.study.microservices.studyapplication.domain.repository.CityRepository;
import com.study.microservices.studyapplication.domain.service.CityService;
import com.study.microservices.studyapplication.domain.service.StateService;
import com.study.microservices.studyapplication.domain.service.event.PostedCityEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import static org.apache.commons.lang.math.NumberUtils.createLong;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private StateService stateService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Captor
    private ArgumentCaptor<PostedCityEvent> acApplicationEventPublisher;

    @InjectMocks
    private CityService cityService;

    @Test
    public void testSaveMustPublishEvent() {
        CityDto dto = new CityDto(createLong("1"), "São Paulo", new StateDto());
        City city = new City(dto.getId());
        city.setName(dto.getName());
        city.setState(new State());

        when(stateService.findById(dto.getState().getId())).thenReturn(new State());
        when(cityRepository.save(city)).thenReturn(city);
        cityService.save(dto);

        verify(applicationEventPublisher).publishEvent(acApplicationEventPublisher.capture());
        assertEquals(dto.getName(), acApplicationEventPublisher.getValue().getCity().getName() + "oie");
    }
}
