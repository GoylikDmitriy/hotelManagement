package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.service.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllHotels_shouldReturnList() throws Exception {
        List<HotelShortResponse> response = List.of(
                new HotelShortResponse(1L, "Hotel 1", "Desc", "Minsk", "+375"),
                new HotelShortResponse(2L, "Hotel 2", "Desc", "Moscow", "+799")
        );

        when(hotelService.getAllHotels()).thenReturn(response);

        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Hotel 1"));
    }

    @Test
    void getHotelById_shouldReturnHotel() throws Exception {
        HotelResponse response = new HotelResponse(
                1L,
                "Hotel",
                "Desc",
                "Hilton",
                null,
                null,
                null,
                null
        );

        when(hotelService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/hotels/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hotel"));
    }

    @Test
    void createHotel_shouldReturn201() throws Exception {
        CreateHotelRequest request = new CreateHotelRequest(
                "Hotel",
                "Desc",
                "Hilton",
                null,
                null,
                null
        );

        HotelShortResponse response =
                new HotelShortResponse(1L, "Hotel", "Desc", "Minsk", "+375");

        when(hotelService.createHotel(any())).thenReturn(response);

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void addAllAmenities_shouldReturn200() throws Exception {
        Set<String> amenities = Set.of("WiFi", "Parking");

        mockMvc.perform(post("/hotels/{id}/amenities", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenities)))
                .andExpect(status().isOk());

        verify(hotelService).addAllAmenities(1L, amenities);
    }
}