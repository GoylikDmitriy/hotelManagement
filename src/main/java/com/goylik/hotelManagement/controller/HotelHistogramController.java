package com.goylik.hotelManagement.controller;

import com.goylik.hotelManagement.service.HotelHistogramService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/histogram")
@RequiredArgsConstructor
@Validated
public class HotelHistogramController {
    private final HotelHistogramService histogramService;

    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable @NotBlank String param) {
        return histogramService.getHistogram(param);
    }
}
