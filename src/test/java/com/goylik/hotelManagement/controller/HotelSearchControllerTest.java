package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.service.HotelSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelSearchController.class)
class HotelSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HotelSearchService hotelSearchService;

    @Test
    void search_shouldReturnHotels() throws Exception {
        List<HotelShortResponse> response = List.of(
                new HotelShortResponse(1L, "Hotel", "Desc", "Minsk", "+375")
        );

        when(hotelSearchService.search(
                eq("hotel"),
                eq("hilton"),
                eq("minsk"),
                eq(null),
                eq(null)
        )).thenReturn(response);

        mockMvc.perform(get("/search")
                        .param("name", "hotel")
                        .param("brand", "hilton")
                        .param("city", "minsk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hotel"));
    }

    @Test
    void search_shouldReturnHotels_byAmenities() throws Exception {
        List<HotelShortResponse> response = List.of(
                new HotelShortResponse(1L, "Hotel", "Desc", "Minsk", "+375")
        );

        when(hotelSearchService.search(
                isNull(),
                isNull(),
                isNull(),
                isNull(),
                eq(List.of("WiFi", "Parking"))
        )).thenReturn(response);

        mockMvc.perform(get("/search")
                        .param("amenities", "WiFi")
                        .param("amenities", "Parking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Hotel"));
    }
}

