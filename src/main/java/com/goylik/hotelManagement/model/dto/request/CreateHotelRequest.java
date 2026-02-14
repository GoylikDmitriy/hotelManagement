package com.goylik.hotelManagement.model.dto.request;

import com.goylik.hotelManagement.model.dto.AddressDto;
import com.goylik.hotelManagement.model.dto.ArrivalTimeDto;
import com.goylik.hotelManagement.model.dto.ContactsDto;

public record CreateHotelRequest(
        String name,
        String description,
        String brand,
        AddressDto address,
        ContactsDto contacts,
        ArrivalTimeDto arrivalTime
) {
}
