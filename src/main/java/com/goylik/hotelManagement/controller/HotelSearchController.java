package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.service.HotelSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelSearchController {
    private final HotelSearchService hotelSearchService;

    @GetMapping("/search")
    public List<HotelShortResponse> search(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String brand,
                                           @RequestParam(required = false) String city,
                                           @RequestParam(required = false) String country,
                                           @RequestParam(required = false) Collection<String> amenities) {
        return hotelSearchService.search(name, brand, city, country, amenities);
    }
}
