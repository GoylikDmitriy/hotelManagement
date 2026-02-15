package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.service.HotelHistogramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/histogram")
@RequiredArgsConstructor
public class HotelHistogramController {
    private final HotelHistogramService histogramService;

    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return histogramService.getHistogram(param);
    }
}
