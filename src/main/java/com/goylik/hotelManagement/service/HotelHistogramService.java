package com.goylik.hotelManagement.service;

import java.util.Map;

public interface HotelHistogramService {
    Map<String, Long> getHistogram(String param);
}
