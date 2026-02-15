package com.goylik.hotelManagement.service.strategy;

import com.goylik.hotelManagement.repository.projection.HistogramEntry;

import java.util.List;

/**
 * Strategy interface for calculating hotel histograms.
 * <p>
 * Implementations define how hotels are grouped
 * and counted by a specific parameter.
 */
public interface HistogramStrategy {
    /**
     * Calculates histogram entries for a specific grouping strategy.
     *
     * @return list of histogram entries containing key and count
     */
    List<HistogramEntry> calculate();
}
