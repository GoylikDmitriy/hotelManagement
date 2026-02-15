package com.goylik.hotelManagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDto(
        @NotBlank @Size(max = 7) String houseNumber,
        @NotBlank @Size(max = 255) String street,
        @NotBlank @Size(max = 127) String city,
        @NotBlank @Size(max = 127) String country,
        @NotBlank @Size(max = 20) String postCode
) {
}
