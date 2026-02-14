package com.goylik.hotelManagement.util.mapper;

import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.model.entity.Hotel;
import com.goylik.hotelManagement.model.entity.embedded.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    Hotel toEntity(CreateHotelRequest request);

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

    @Named("addressToString")
    default String addressToString(Address address) {
        return address.toString();
    }
}
