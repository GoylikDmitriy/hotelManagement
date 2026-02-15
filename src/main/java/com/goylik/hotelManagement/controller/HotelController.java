package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public List<HotelShortResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelResponse getHotelById(@PathVariable Long id) {
        return hotelService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelShortResponse createHotel(@RequestBody CreateHotelRequest request) {
        return hotelService.createHotel(request);
    }

    @PostMapping("/{id}/amenities")
    @ResponseStatus(HttpStatus.OK)
    public void addAllAmenities(@PathVariable Long id, @RequestBody Set<String> amenities) {
        hotelService.addAllAmenities(id, amenities);
    }
}
