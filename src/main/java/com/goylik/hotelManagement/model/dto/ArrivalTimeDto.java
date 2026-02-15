package com.goylik.hotelManagement.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ArrivalTimeDto(
        @JsonFormat(pattern = "HH:mm") @NotNull LocalTime checkIn,
        @JsonFormat(pattern = "HH:mm") LocalTime checkOut
) {
}
