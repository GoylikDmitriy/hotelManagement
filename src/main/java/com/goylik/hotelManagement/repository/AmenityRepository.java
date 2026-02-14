package com.goylik.hotelManagement.repository;

import com.goylik.hotelManagement.model.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    List<Amenity> findAllByNameInIgnoreCase(Collection<String> names);
}