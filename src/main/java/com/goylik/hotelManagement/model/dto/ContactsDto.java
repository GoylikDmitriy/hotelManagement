package com.goylik.hotelManagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactsDto(
        @NotBlank @Size(max = 50) String phone,
        @NotBlank @Size(max = 255) String email
) {
}
