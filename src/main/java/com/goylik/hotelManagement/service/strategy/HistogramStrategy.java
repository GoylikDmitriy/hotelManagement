package com.goylik.hotelManagement.service.strategy;

import com.goylik.hotelManagement.repository.projection.HistogramEntry;

import java.util.List;

public interface HistogramStrategy {
    List<HistogramEntry> calculate();
}
