package com.goylik.hotelManagement.service.impl;

import com.goylik.hotelManagement.exception.UnsupportedHistogramParamException;
import com.goylik.hotelManagement.repository.projection.HistogramEntry;
import com.goylik.hotelManagement.service.HotelHistogramService;
import com.goylik.hotelManagement.service.strategy.HistogramStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelHistogramServiceImpl implements HotelHistogramService {
    private final Map<String, HistogramStrategy> strategies;

    @Override
    public Map<String, Long> getHistogram(String param) {
        HistogramStrategy strategy = strategies.get(param.toLowerCase());
        if (strategy == null) {
            throw new UnsupportedHistogramParamException(param);
        }

        return strategy.calculate().stream()
                .collect(Collectors.toMap(
                        HistogramEntry::getKey,
                        HistogramEntry::getCount)
                );
    }
}
