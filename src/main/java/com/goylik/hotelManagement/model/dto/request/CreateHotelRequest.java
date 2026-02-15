package com.goylik.hotelManagement.model.dto.request;

import com.goylik.hotelManagement.model.dto.AddressDto;
import com.goylik.hotelManagement.model.dto.ArrivalTimeDto;
import com.goylik.hotelManagement.model.dto.ContactsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateHotelRequest(
        @NotBlank @Size(max = 255) String name,
        String description,
        @NotBlank @Size(max = 127) String brand,
        @NotNull @Valid AddressDto address,
        @NotNull @Valid ContactsDto contacts,
        @NotNull @Valid ArrivalTimeDto arrivalTime
) {
}
