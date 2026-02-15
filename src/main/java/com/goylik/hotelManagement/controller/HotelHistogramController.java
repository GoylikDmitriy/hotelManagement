package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.service.HotelHistogramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/histogram")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Hotel search",
        description = "Search hotels by various parameters"
)
public class HotelHistogramController {
    private final HotelHistogramService histogramService;

    @Operation(
            summary = "Search hotels",
            description = "Search hotels by optional parameters: name, brand, city, country and amenities"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search result returned successfully"
    )
    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable @NotBlank String param) {
        return histogramService.getHistogram(param);
    }
}
