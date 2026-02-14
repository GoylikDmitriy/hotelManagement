package com.goylik.hotelManagement.service.impl;

import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.model.entity.Amenity;
import com.goylik.hotelManagement.model.entity.Hotel;
import com.goylik.hotelManagement.repository.AmenityRepository;
import com.goylik.hotelManagement.repository.HotelRepository;
import com.goylik.hotelManagement.service.HotelService;
import com.goylik.hotelManagement.exception.HotelNotFoundException;
import com.goylik.hotelManagement.util.mapper.HotelMapper;
import com.goylik.hotelManagement.util.specification.HotelSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final HotelMapper hotelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortResponse> getAllHotels() {
        var hotels = hotelRepository.findAll();
        return mapToShortResponse(hotels);
    }

    private List<HotelShortResponse> mapToShortResponse(List<Hotel> hotels) {
        return hotels.stream()
                .map(hotelMapper::toShortResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortResponse> search(String name, String brand, String city,
                                           String country, Collection<String> amenities) {
        var specs = HotelSpecifications.buildSpecification(name, brand, city, country, amenities);
        var hotelsWithSpecs = hotelRepository.findAll(specs);
        return mapToShortResponse(hotelsWithSpecs);
    }

    @Override
    @Transactional(readOnly = true)
    public HotelResponse getById(Long id) {
        var hotel = fetchByIdWithAmenitiesOrThrow(id);
        return hotelMapper.toResponse(hotel);
    }

    private Hotel fetchByIdWithAmenitiesOrThrow(Long id) {
        return hotelRepository.findByIdWithAmenities(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    @Override
    @Transactional
    public HotelShortResponse createHotel(CreateHotelRequest request) {
        var hotelToCreate = hotelMapper.toEntity(request);
        var createdHotel = hotelRepository.save(hotelToCreate);
        return hotelMapper.toShortResponse(createdHotel);
    }

    @Override
    @Transactional
    public void addAllAmenities(Long id, Set<String> amenities) {
        var hotel = fetchByIdWithAmenitiesOrThrow(id);

        var existingByName = findExistingAmenitiesAndCollectToMap(amenities);

        existingByName.values().forEach(hotel::addAmenity);

        amenities.stream()
                .filter(name -> !existingByName.containsKey(name.toLowerCase()))
                .map(Amenity::new)
                .forEach(hotel::addAmenity);
    }

    private Map<String, Amenity> findExistingAmenitiesAndCollectToMap(Set<String> amenityNames) {
        return amenityRepository.findAllByNameInIgnoreCase(amenityNames).stream()
                .collect(Collectors.toMap(
                        a -> a.getName().toLowerCase(),
                        Function.identity()
                ));
    }
}