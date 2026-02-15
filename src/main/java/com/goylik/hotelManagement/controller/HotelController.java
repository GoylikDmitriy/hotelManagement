package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Hotels",
        description = "Operations for managing hotels"
)
public class HotelController {
    private final HotelService hotelService;

    @Operation(
            summary = "Get all hotels",
            description = "Returns a list of all hotels with short information"
    )
    @ApiResponse(responseCode = "200", description = "List of hotels returned successfully")
    @GetMapping
    public List<HotelShortResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(
            summary = "Get hotel by id",
            description = "Returns full hotel information by its identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel found"),
            @ApiResponse(responseCode = "404", description = "Hotel not found"),
            @ApiResponse(responseCode = "400", description = "Invalid hotel id")
    })
    @GetMapping("/{id}")
    public HotelResponse getHotelById(@PathVariable @Positive Long id) {
        return hotelService.getById(id);
    }

    @Operation(
            summary = "Create hotel",
            description = "Creates a new hotel and return hotel with short information"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Hotel created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelShortResponse createHotel(@RequestBody @Valid CreateHotelRequest request) {
        return hotelService.createHotel(request);
    }

    @Operation(
            summary = "Add amenities to hotel",
            description = "Adds a list of amenities to an existing hotel"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Amenities added successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/{id}/amenities")
    @ResponseStatus(HttpStatus.OK)
    public void addAllAmenities(@PathVariable @Positive Long id,
                                @RequestBody @NotEmpty Set<@NotBlank String> amenities) {
        hotelService.addAllAmenities(id, amenities);
    }
}
