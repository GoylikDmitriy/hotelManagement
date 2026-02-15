package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.service.HotelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Validated
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public List<HotelShortResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public HotelResponse getHotelById(@PathVariable @Positive Long id) {
        return hotelService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelShortResponse createHotel(@RequestBody @Valid CreateHotelRequest request) {
        return hotelService.createHotel(request);
    }

    @PostMapping("/{id}/amenities")
    @ResponseStatus(HttpStatus.OK)
    public void addAllAmenities(@PathVariable @Positive Long id,
                                @RequestBody @NotEmpty Set<@NotBlank String> amenities) {
        hotelService.addAllAmenities(id, amenities);
    }
}
