package com.goylik.hotelManagement.service;

import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;

import java.util.Collection;
import java.util.List;

public interface HotelSearchService {
    List<HotelShortResponse> search(String name, String brand, String city,
                                    String country, Collection<String> amenities);
}
