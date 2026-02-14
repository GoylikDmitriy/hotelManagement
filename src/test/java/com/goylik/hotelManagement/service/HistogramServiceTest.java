package com.goylik.hotelManagement.service;

import com.goylik.hotelManagement.exception.UnsupportedHistogramParamException;
import com.goylik.hotelManagement.repository.projection.HistogramEntry;
import com.goylik.hotelManagement.service.impl.HistogramServiceImpl;
import com.goylik.hotelManagement.service.strategy.HistogramStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistogramServiceTest {
    @Mock
    private HistogramStrategy amenitiesStrategy;
    @Mock
    private HistogramStrategy brandStrategy;
    @Mock
    private HistogramStrategy cityStrategy;
    @Mock
    private HistogramStrategy countryStrategy;

    private HistogramServiceImpl histogramService;

    @BeforeEach
    void setUp() {
        Map<String, HistogramStrategy> strategies = Map.of(
                "amenities", amenitiesStrategy,
                "brand", brandStrategy,
                "city", cityStrategy,
                "country", countryStrategy
        );

        histogramService = new HistogramServiceImpl(strategies);
    }

    @Test
    void getHistogram_ShouldReturnMappedResult_ForAmenities() {
        List<HistogramEntry> entries = List.of(
                new TestHistogramEntry("Free WiFi", 10L),
                new TestHistogramEntry("Parking", 5L)
        );

        when(amenitiesStrategy.calculate()).thenReturn(entries);

        Map<String, Long> result = histogramService.getHistogram("amenities");

        assertEquals(2, result.size());
        assertEquals(10L, result.get("Free WiFi"));
        assertEquals(5L, result.get("Parking"));
        verify(amenitiesStrategy).calculate();
    }

    @Test
    void getHistogram_ShouldReturnMappedResult_ForBrand() {
        List<HistogramEntry> entries = List.of(
                new TestHistogramEntry("Hilton", 3L),
                new TestHistogramEntry("Marriott", 7L)
        );
        when(brandStrategy.calculate()).thenReturn(entries);

        Map<String, Long> result = histogramService.getHistogram("brand");

        assertEquals(2, result.size());
        assertEquals(3L, result.get("Hilton"));
        assertEquals(7L, result.get("Marriott"));
        verify(brandStrategy).calculate();
    }

    @Test
    void getHistogram_ShouldReturnMappedResult_ForCity() {
        List<HistogramEntry> entries = List.of(
                new TestHistogramEntry("Minsk", 2L),
                new TestHistogramEntry("Mogilev", 4L)
        );
        when(cityStrategy.calculate()).thenReturn(entries);

        Map<String, Long> result = histogramService.getHistogram("city");

        assertEquals(2, result.size());
        assertEquals(2L, result.get("Minsk"));
        assertEquals(4L, result.get("Mogilev"));
        verify(cityStrategy).calculate();
    }

    @Test
    void getHistogram_ShouldReturnMappedResult_ForCountry() {
        List<HistogramEntry> entries = List.of(
                new TestHistogramEntry("Belarus", 2L)
        );
        when(countryStrategy.calculate()).thenReturn(entries);

        Map<String, Long> result = histogramService.getHistogram("country");

        assertEquals(1, result.size());
        assertEquals(2L, result.get("Belarus"));
        verify(countryStrategy).calculate();
    }

    @Test
    void getHistogram_ShouldBeCaseInsensitive() {
        List<HistogramEntry> entries = List.of(new TestHistogramEntry("Hilton", 3L));
        when(brandStrategy.calculate()).thenReturn(entries);

        Map<String, Long> result = histogramService.getHistogram("BRAND");

        assertEquals(1, result.size());
        assertEquals(3L, result.get("Hilton"));
        verify(brandStrategy).calculate();
    }

    @Test
    void getHistogram_ShouldThrowException_ForUnknownParam() {
        String unknownParam = "invalid";
        UnsupportedHistogramParamException ex = assertThrows(
                UnsupportedHistogramParamException.class,
                () -> histogramService.getHistogram(unknownParam)
        );

        assertTrue(ex.getMessage().contains(unknownParam));
    }

    private record TestHistogramEntry(String key, Long count) implements HistogramEntry {
        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Long getCount() {
            return count;
        }
    }
}