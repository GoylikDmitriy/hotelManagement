package com.goylik.hotelManagement.service;

import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;

import java.util.Collection;
import java.util.List;

/**
 * Service for searching hotels by various criteria.
 */
public interface HotelSearchService {
    /**
     * Searches hotels using optional filter parameters.
     * <p>
     * All search parameters are optional and can be combined.
     *
     * @param name hotel name (partial match, case-insensitive)
     * @param brand hotel brand
     * @param city city where hotel is located
     * @param country country where hotel is located
     * @param amenities collection of required amenities
     * @return list of hotels matching search criteria
     */
    List<HotelShortResponse> search(String name, String brand, String city,
                                    String country, Collection<String> amenities);
}
