package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.service.HotelHistogramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelHistogramController.class)
class HotelHistogramControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HotelHistogramService histogramService;

    @Test
    void getHistogram_shouldReturnMap() throws Exception {
        Map<String, Long> histogram = Map.of(
                "Minsk", 2L,
                "Moscow", 5L
        );

        when(histogramService.getHistogram("city")).thenReturn(histogram);

        mockMvc.perform(get("/histogram/{param}", "city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Minsk").value(2))
                .andExpect(jsonPath("$.Moscow").value(5));
    }

    @Test
    void getHistogram_amenities_singleAmenity() throws Exception {
        Map<String, Long> histogram = Map.of(
                "Free WiFi", 10L
        );

        when(histogramService.getHistogram("amenities")).thenReturn(histogram);

        mockMvc.perform(get("/histogram/{param}", "amenities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Free WiFi']").value(10));
    }

    @Test
    void getHistogram_amenities_multipleAmenities() throws Exception {
        Map<String, Long> histogram = Map.of(
                "Free WiFi", 10L,
                "Parking", 7L,
                "Fitness Center", 3L
        );

        when(histogramService.getHistogram("amenities")).thenReturn(histogram);

        mockMvc.perform(get("/histogram/{param}", "amenities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Free WiFi']").value(10))
                .andExpect(jsonPath("$.Parking").value(7))
                .andExpect(jsonPath("$.['Fitness Center']").value(3));
    }

    @Test
    void getHistogram_amenities_emptyResult() throws Exception {
        when(histogramService.getHistogram("amenities"))
                .thenReturn(Collections.emptyMap());

        mockMvc.perform(get("/histogram/{param}", "amenities"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}

