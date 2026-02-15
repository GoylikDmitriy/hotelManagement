package com.goylik.hotelManagement.service.impl;

import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.model.entity.Hotel;
import com.goylik.hotelManagement.repository.HotelRepository;
import com.goylik.hotelManagement.service.HotelSearchService;
import com.goylik.hotelManagement.util.mapper.HotelMapper;
import com.goylik.hotelManagement.util.specification.HotelSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelSearchServiceImpl implements HotelSearchService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortResponse> search(String name, String brand, String city,
                                           String country, Collection<String> amenities) {
        var specs = HotelSpecifications.buildSpecification(name, brand, city, country, amenities);
        var hotelsWithSpecs = hotelRepository.findAll(specs);
        return mapToShortResponse(hotelsWithSpecs);
    }

    private List<HotelShortResponse> mapToShortResponse(List<Hotel> hotels) {
        return hotels.stream()
                .map(hotelMapper::toShortResponse)
                .toList();
    }
}
