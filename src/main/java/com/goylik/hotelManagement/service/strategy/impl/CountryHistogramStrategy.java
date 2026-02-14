package com.goylik.hotelManagement.service.strategy.impl;

import com.goylik.hotelManagement.repository.HotelHistogramRepository;
import com.goylik.hotelManagement.repository.projection.HistogramEntry;
import com.goylik.hotelManagement.service.strategy.HistogramStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("country")
@RequiredArgsConstructor
public class CountryHistogramStrategy implements HistogramStrategy {
    private final HotelHistogramRepository histogramRepository;

    @Override
    public List<HistogramEntry> calculate() {
        return histogramRepository.countGroupByCountry();
    }
}
