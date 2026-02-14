package com.goylik.hotelManagement.model.dto.response;

import com.goylik.hotelManagement.model.dto.AddressDto;
import com.goylik.hotelManagement.model.dto.AmenityDto;
import com.goylik.hotelManagement.model.dto.ArrivalTimeDto;
import com.goylik.hotelManagement.model.dto.ContactsDto;

import java.util.List;

public record HotelResponse(
        Long id,
        String name,
        String description,
        String brand,
        AddressDto address,
        ContactsDto contacts,
        ArrivalTimeDto arrivalTime,
        List<AmenityDto> amenities
) {
}
