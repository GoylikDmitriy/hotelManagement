package com.goylik.hotelManagement.service;

import com.goylik.hotelManagement.exception.HotelNotFoundException;
import com.goylik.hotelManagement.model.dto.AddressDto;
import com.goylik.hotelManagement.model.dto.ArrivalTimeDto;
import com.goylik.hotelManagement.model.dto.ContactsDto;
import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.model.entity.Amenity;
import com.goylik.hotelManagement.model.entity.Hotel;
import com.goylik.hotelManagement.repository.AmenityRepository;
import com.goylik.hotelManagement.repository.HotelRepository;
import com.goylik.hotelManagement.service.impl.HotelServiceImpl;
import com.goylik.hotelManagement.util.mapper.HotelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {
    @Mock private HotelRepository hotelRepository;
    @Mock private AmenityRepository amenityRepository;
    @Mock private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void getAllHotels_ShouldReturnMappedShortResponses() {
        Hotel hotel = new Hotel();
        List<Hotel> hotels = List.of(hotel);

        HotelShortResponse shortResponse = new HotelShortResponse(
                1L, "Hotel A", "Desc", "123 Main St", "+123456789"
        );

        when(hotelRepository.findAll()).thenReturn(hotels);
        when(hotelMapper.toShortResponse(hotel)).thenReturn(shortResponse);

        List<HotelShortResponse> result = hotelService.getAllHotels();

        assertEquals(1, result.size());
        assertEquals(shortResponse, result.getFirst());

        verify(hotelRepository).findAll();
        verify(hotelMapper).toShortResponse(hotel);
    }

    @Test
    void getById_ShouldReturnHotelResponse() {
        Hotel hotel = new Hotel();
        HotelResponse response = new HotelResponse(
                1L, "Hotel", "Desc", "Brand",
                new AddressDto("123", "Main St", "City", "Country", "12345"),
                new ContactsDto("+123", "email@test.com"),
                new ArrivalTimeDto(LocalTime.of(10, 0), LocalTime.of(12, 0)),
                Set.of("WiFi")
        );

        when(hotelRepository.findByIdWithAmenities(1L)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toResponse(hotel)).thenReturn(response);

        HotelResponse result = hotelService.getById(1L);

        assertEquals(response, result);
        verify(hotelRepository).findByIdWithAmenities(1L);
        verify(hotelMapper).toResponse(hotel);
    }

    @Test
    void getById_ShouldThrow_WhenHotelNotFound() {
        when(hotelRepository.findByIdWithAmenities(1L)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.getById(1L));
    }

    @Test
    void createHotel_ShouldSaveAndReturnShortResponse() {
        CreateHotelRequest request = new CreateHotelRequest(
                "Hotel C", "Desc C", "Brand C",
                new AddressDto("1", "Street", "City", "Country", "00000"),
                new ContactsDto("+111", "test@test.com"),
                new ArrivalTimeDto(LocalTime.of(10, 0), LocalTime.of(12, 0))
        );

        Hotel hotelEntity = new Hotel();
        Hotel savedHotel = new Hotel();
        HotelShortResponse shortResponse = new HotelShortResponse(
                3L, "Hotel C", "Desc C", "1 Street", "+111"
        );

        when(hotelMapper.toEntity(request)).thenReturn(hotelEntity);
        when(hotelRepository.save(hotelEntity)).thenReturn(savedHotel);
        when(hotelMapper.toShortResponse(savedHotel)).thenReturn(shortResponse);

        HotelShortResponse result = hotelService.createHotel(request);

        assertEquals(shortResponse, result);
        verify(hotelMapper).toEntity(request);
        verify(hotelRepository).save(hotelEntity);
        verify(hotelMapper).toShortResponse(savedHotel);
    }

    @Test
    void addAllAmenities_ShouldAddExistingAndNewAmenities() {
        Hotel hotel = spy(new Hotel());
        Set<String> inputAmenities = Set.of("Free WiFi", "Parking");

        when(hotelRepository.findByIdWithAmenities(1L)).thenReturn(Optional.of(hotel));

        Amenity wifi = new Amenity("Free WiFi");

        when(amenityRepository.findAllByNameInIgnoreCase(anySet())).thenReturn(List.of(wifi));

        hotelService.addAllAmenities(1L, inputAmenities);

        ArgumentCaptor<Set<Amenity>> captor = ArgumentCaptor.forClass(Set.class);
        verify(hotel, times(1)).addAllAmenities(captor.capture());

        Set<Amenity> added = captor.getValue();
        assertTrue(added.stream().anyMatch(a -> a.getName().equals("Free WiFi")));
        assertTrue(added.stream().anyMatch(a -> a.getName().equals("Parking")));
    }

    @Test
    void addAllAmenities_ShouldThrow_WhenHotelNotFound() {
        when(hotelRepository.findByIdWithAmenities(1L)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.addAllAmenities(1L, Set.of("WiFi")));
    }
}

