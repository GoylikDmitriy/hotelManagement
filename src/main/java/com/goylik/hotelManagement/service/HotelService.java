package com.goylik.hotelManagement.service;

import com.goylik.hotelManagement.exception.HotelNotFoundException;
import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;

import java.util.List;
import java.util.Set;

/**
 * Service for managing hotels.
 * <p>
 * Provides operations for creating hotels, retrieving hotel information
 * and managing hotel amenities.
 */
public interface HotelService {

    /**
     * Returns a list of all hotels with short information.
     *
     * @return list of hotels in short representation
     */
    List<HotelShortResponse> getAllHotels();

    /**
     * Returns full information about a hotel by its identifier.
     *
     * @param id hotel identifier (must be positive)
     * @return full hotel information
     * @throws HotelNotFoundException if hotel with given id does not exist
     */
    HotelResponse getById(Long id);

    /**
     * Creates a new hotel.
     *
     * @param request data required to create a hotel
     * @return created hotel short representation
     */
    HotelShortResponse createHotel(CreateHotelRequest request);

    /**
     * Adds a set of amenities to an existing hotel.
     * <p>
     * If an amenity does not exist, it will be created.
     *
     * @param id hotel identifier
     * @param amenities set of amenity names to add
     * @throws HotelNotFoundException if hotel with given id does not exist
     */
    void addAllAmenities(Long id, Set<String> amenities);
}
