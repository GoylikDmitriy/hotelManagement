package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.service.HotelSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Hotel histograms",
        description = "Histogram statistics for hotels"
)
public class HotelSearchController {
    private final HotelSearchService hotelSearchService;

    @Operation(
            summary = "Get hotel histogram",
            description = """
                    Returns histogram grouped by given parameter.
                    Supported values: brand, city, country, amenities.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histogram returned successfully"),
            @ApiResponse(responseCode = "400", description = "Unsupported histogram parameter")
    })
    @GetMapping("/search")
    public List<HotelShortResponse> search(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String brand,
                                           @RequestParam(required = false) String city,
                                           @RequestParam(required = false) String country,
                                           @RequestParam(required = false) Collection<String> amenities) {
        return hotelSearchService.search(name, brand, city, country, amenities);
    }
}
