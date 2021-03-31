package com.study.microservices.studyapplication.service;

import com.study.microservices.studyapplication.domain.dto.RestaurantDto;
import com.study.microservices.studyapplication.domain.exception.EntityAlreadyInUseException;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import com.study.microservices.studyapplication.domain.model.Kitchen;
import com.study.microservices.studyapplication.domain.repository.KitchenRepository;
import com.study.microservices.studyapplication.domain.service.KitchenService;
import com.study.microservices.studyapplication.domain.service.RestaurantService;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KitchenServiceTest {

    @Mock
    private KitchenRepository repository;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private KitchenService service;

    private static final Long KITCHEN_ID = NumberUtils.createLong("1");

    @Test
    public void testDeleteKitchenSuccess() {
        when(restaurantService.searchByKitchen(KITCHEN_ID)).thenReturn(Optional.of(new ArrayList<>()));
        when(repository.findById(KITCHEN_ID)).thenReturn(Optional.of(new Kitchen()));
        service.delete(KITCHEN_ID);

        verify(restaurantService, times(3)).searchByKitchen(anyLong());
        verify(repository).findById(anyLong());
        verify(repository).delete(any(Kitchen.class));
    }

    @Test
    public void testDeleteKitchenRelatedARestaurantSMustFailed() {
        when(restaurantService.searchByKitchen(KITCHEN_ID)).thenReturn(Optional.of(List.of(new RestaurantDto())));
        assertThatThrownBy(() ->  service.delete(KITCHEN_ID))
                .isInstanceOf(EntityAlreadyInUseException.class)
                .hasMessage(format("There is/are restaurant(s) associate a kitchen of id.: %s", KITCHEN_ID));

        verify(restaurantService).searchByKitchen(anyLong());
        verifyZeroInteractions(repository);
    }

    @Test
    public void testDeleteKitchenNonexistentMustFailed() {
        when(restaurantService.searchByKitchen(KITCHEN_ID)).thenReturn(Optional.of(new ArrayList<>()));
        when(repository.findById(KITCHEN_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() ->  service.delete(KITCHEN_ID))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessage(format("Kitchen of id %s not found.", KITCHEN_ID));

        verify(restaurantService).searchByKitchen(anyLong());
        verify(repository).findById(anyLong());
        verifyNoMoreInteractions(repository);
    }
}
