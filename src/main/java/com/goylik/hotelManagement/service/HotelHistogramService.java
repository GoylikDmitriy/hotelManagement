package com.goylik.hotelManagement.service;

import com.goylik.hotelManagement.exception.UnsupportedHistogramParamException;

import java.util.Map;

/**
 * Service for building histograms of hotels grouped by a specific parameter.
 */
public interface HotelHistogramService {
    /**
     * Returns a histogram of hotels grouped by the given parameter.
     * <p>
     * Supported parameters:
     * <ul>
     *     <li>brand</li>
     *     <li>city</li>
     *     <li>country</li>
     *     <li>amenities</li>
     * </ul>
     *
     * @param param grouping parameter
     * @return map where key is parameter value and value is number of hotels
     * @throws UnsupportedHistogramParamException if parameter is not supported
     */
    Map<String, Long> getHistogram(String param);
}
