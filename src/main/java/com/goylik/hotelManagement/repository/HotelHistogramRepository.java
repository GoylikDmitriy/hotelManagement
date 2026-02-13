package com.goylik.hotelManagement.repository;

import com.goylik.hotelManagement.repository.projection.HistogramEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelHistogramRepository {
    @Query("""
            select h.brand as key, count(h) as count
            from Hotel h
            group by h.brand
            """)
    List<HistogramEntry> countGroupByBrand();

    @Query("""
            select h.address.city as key, count(h) as count
            from Hotel h
            group by h.address.city
            """)
    List<HistogramEntry> countGroupByCity();

    @Query("""
            select h.address.country as key, count(h) as count
            from Hotel h
            group by h.address.country
            """)
    List<HistogramEntry> countGroupByCountry();

    @Query("""
            select a.name as key, count(h) as count
            from Amenity a join a.hotels h
            group by a.id, a.name
            """)
    List<HistogramEntry> countGroupByAmenities();
}
