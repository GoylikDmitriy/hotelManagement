package com.goylik.hotelManagement.model.entity;

import com.goylik.hotelManagement.model.entity.embedded.Address;
import com.goylik.hotelManagement.model.entity.embedded.ArrivalTime;
import com.goylik.hotelManagement.model.entity.embedded.Contacts;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hotels")
@Getter @Setter
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String brand;

    @Embedded
    private Address address;

    @Embedded
    private Contacts contacts;

    @Embedded
    private ArrivalTime arrivalTime;

    @ManyToMany
    @JoinTable(
            name = "hotel_amenity",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", address=" + address +
                ", contacts=" + contacts +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return id == hotel.id &&
                Objects.equals(name, hotel.name) &&
                Objects.equals(brand, hotel.brand) &&
                Objects.equals(address, hotel.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, address);
    }
}
