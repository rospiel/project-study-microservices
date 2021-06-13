package com.study.microservices.studyapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.microservices.studyapplication.api.controller.KitchenController;
import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.service.KitchenService;
import com.study.microservices.studyapplication.domain.service.kafka.KafkaReceive;
import com.study.microservices.studyapplication.domain.service.kafka.KafkaSend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KitchenController.class)
public class KitchenControllerTest {

    @MockBean
    private KitchenService service;

    @MockBean
    private KafkaSend kafkaSend;

    @MockBean
    private KafkaReceive kafkaReceive;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private static final String URL = "/kitchens";
    private static final String URL_BY_ID = URL + "/{kitchenId}";

    @Test
    public void testInsertKitchenHttp201() throws Exception {
        mockMvc.perform(post(URL)
                .content(mapper.writeValueAsString(new KitchenDto(null, "Indian")))
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        verify(service).save(any(KitchenDto.class));
    }

    @Test
    public void testInsertKitchenEmptyNameHttp400() throws Exception {
        mockMvc.perform(post(URL)
                .content(mapper.writeValueAsString(new KitchenDto(null, "")))
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(service);
    }

    @Test
    public void testGetAllKitchenHttp200() throws Exception {
        KitchenDto kitchen = new KitchenDto(2L, "Indian");
        when(service.findAll()).thenReturn(List.of(kitchen, kitchen));

        mockMvc.perform(get(URL)
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(kitchen.getId()))
                .andExpect(jsonPath("$[0].name").value(kitchen.getName()));

        verify(service).findAll();
    }

    @Test
    public void testGetKitchenByIdHttp200() throws Exception {
        KitchenDto kitchen = new KitchenDto(2L, "Indian");
        when(service.searchById(kitchen.getId())).thenReturn(kitchen);

        mockMvc.perform(get(URL_BY_ID, kitchen.getId())
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id").value(kitchen.getId()))
                .andExpect(jsonPath("$.name").value(kitchen.getName()));

        verify(service).searchById(anyLong());
    }
}
