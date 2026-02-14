package com.goylik.hotelManagement.util.mapper;

import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.model.entity.Amenity;
import com.goylik.hotelManagement.model.entity.Hotel;
import com.goylik.hotelManagement.model.entity.embedded.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    Hotel toEntity(CreateHotelRequest request);

    @Mapping(
            target = "amenities",
            source = "amenities",
            qualifiedByName = "mapAmenities"
    )
    HotelResponse toResponse(Hotel hotel);

    @Mapping(
            target = "address",
            source = "address",
            qualifiedByName = "addressToString"
    )
    @Mapping(
            target = "phone",
            source = "contacts.phone"
    )
    HotelShortResponse toShortResponse(Hotel hotel);

    @Named("mapAmenities")
    default Set<String> mapAmenities(Set<Amenity> amenities) {
        if (amenities == null) return Set.of();
        return amenities.stream()
                .map(Amenity::getName)
                .collect(Collectors.toSet());
    }

    @Named("addressToString")
    default String addressToString(Address address) {
        return address.toString();
    }
}
