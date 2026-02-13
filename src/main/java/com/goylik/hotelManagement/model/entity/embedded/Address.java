package com.goylik.hotelManagement.model.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter @Setter
public class Address {
    @Column(name = "house_number")
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    @Column(name = "post_code")
    private String postCode;

    @Override
    public String toString() {
        return houseNumber + ' '
                + street + ", "
                + city + ", "
                + postCode + ", "
                + country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(houseNumber, address.houseNumber) &&
                Objects.equals(street, address.street) &&
                Objects.equals(city, address.city) &&
                Objects.equals(country, address.country) &&
                Objects.equals(postCode, address.postCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseNumber, street, city, country, postCode);
    }
}
