package com.goylik.hotelManagement.repository;

import com.goylik.hotelManagement.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("""
           select h from Hotel h
           left join fetch h.amenities
           where h.id = :id
           """)
    Optional<Hotel> findByIdWithAmenities(@Param("id") Long id);
}
