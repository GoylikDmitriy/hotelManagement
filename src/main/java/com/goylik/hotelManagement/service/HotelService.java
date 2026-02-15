package com.goylik.hotelManagement.service;

import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;

import java.util.List;
import java.util.Set;

public interface HotelService {
    List<HotelShortResponse> getAllHotels();
    HotelResponse getById(Long id);
    HotelShortResponse createHotel(CreateHotelRequest request);
    void addAllAmenities(Long id, Set<String> amenities);
}