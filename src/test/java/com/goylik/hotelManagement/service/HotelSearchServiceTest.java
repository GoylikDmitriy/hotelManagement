package com.goylik.hotelManagement.service;

import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.model.entity.Hotel;
import com.goylik.hotelManagement.repository.HotelRepository;
import com.goylik.hotelManagement.service.impl.HotelSearchServiceImpl;
import com.goylik.hotelManagement.util.mapper.HotelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelSearchServiceTest {
    @Mock private HotelRepository hotelRepository;
    @Mock private HotelMapper hotelMapper;

    @InjectMocks
    private HotelSearchServiceImpl hotelSearchService;

    @Test
    void search_ShouldReturnMappedShortResponses() {
        Hotel hotel = new Hotel();
        List<Hotel> hotels = List.of(hotel);

        HotelShortResponse shortResponse = new HotelShortResponse(
                2L, "Hotel B", "Desc B", "456 Elm St", "+987654321"
        );

        when(hotelRepository.findAll(any(Specification.class)))
                .thenReturn(hotels);

        when(hotelMapper.toShortResponse(hotel)).thenReturn(shortResponse);

        List<HotelShortResponse> result = hotelSearchService.search(
                "name", "brand", "city", "country", List.of("WiFi")
        );

        assertEquals(1, result.size());
        assertEquals(shortResponse, result.getFirst());

        verify(hotelRepository).findAll(any(Specification.class));
        verify(hotelMapper).toShortResponse(hotel);
    }
}
