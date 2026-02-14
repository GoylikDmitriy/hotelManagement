package com.goylik.hotelManagement.util.specification;

import com.goylik.hotelManagement.model.entity.Amenity;
import com.goylik.hotelManagement.model.entity.Hotel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public final class HotelSpecifications {
    private HotelSpecifications() {}

    public static Specification<Hotel> hasNameLike(String name) {
        return (root, query, cb) ->
                (name == null || name.isBlank()) ? cb.conjunction() :
                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) ->
                (brand == null || brand.isBlank()) ? cb.conjunction() :
                        cb.equal(cb.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) ->
                (city == null || city.isBlank()) ? cb.conjunction() :
                        cb.equal(cb.lower(root.get("address").get("city")), city.toLowerCase());
    }

    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) ->
                (country == null || country.isBlank()) ? cb.conjunction() :
                        cb.equal(cb.lower(root.get("address").get("country")), country.toLowerCase());
    }

    public static Specification<Hotel> hasAllAmenities(Collection<String> amenities) {
        return (root, query, cb) -> {
            if (amenities == null || amenities.isEmpty()) {
                return cb.conjunction();
            }

            Set<String> lowerCaseAmenities = amenities.stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Hotel> subRoot = subquery.from(Hotel.class);
            Join<Hotel, Amenity> subJoin = subRoot.join("amenities");

            subquery.select(subRoot.get("id"))
                    .where(cb.lower(subJoin.get("name")).in(lowerCaseAmenities))
                    .groupBy(subRoot.get("id"))
                    .having(cb.equal(cb.countDistinct(subJoin), lowerCaseAmenities.size()));

            return root.get("id").in(subquery);
        };
    }

    public static Specification<Hotel> buildSpecification(String name, String brand,
                                                   String city, String country,
                                                   Collection<String> amenities) {
        return Specification
                .where(hasNameLike(name))
                .and(hasBrand(brand))
                .and(hasCity(city))
                .and(hasCountry(country))
                .and(hasAllAmenities(amenities));
    }
}
