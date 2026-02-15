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

/**
 * Utility class containing JPA Specifications for searching hotels.
 * <p>
 * Provides reusable predicates for filtering hotels
 * by various optional criteria.
 */
public final class HotelSpecifications {
    private HotelSpecifications() {}

    /**
     * Creates specification for filtering hotels by name.
     * <p>
     * Performs case-insensitive partial match.
     *
     * @param name hotel name filter
     * @return specification for hotel name filtering
     */
    public static Specification<Hotel> hasNameLike(String name) {
        return (root, query, cb) ->
                (name == null || name.isBlank()) ? cb.conjunction() :
                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    /**
     * Creates specification for filtering hotels by brand.
     * <p>
     * Comparison is case-insensitive.
     *
     * @param brand hotel brand
     * @return specification for hotel brand filtering
     */
    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) ->
                (brand == null || brand.isBlank()) ? cb.conjunction() :
                        cb.equal(cb.lower(root.get("brand")), brand.toLowerCase());
    }

    /**
     * Creates specification for filtering hotels by city.
     * <p>
     * Comparison is case-insensitive
     *
     * @param city city name
     * @return specification for hotel city filtering
     */
    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) ->
                (city == null || city.isBlank()) ? cb.conjunction() :
                        cb.equal(cb.lower(root.get("address").get("city")), city.toLowerCase());
    }

    /**
     * Creates specification for filtering hotels by country.
     * <p>
     * Comparison is case-insensitive
     *
     * @param country country name
     * @return specification for hotel country filtering
     */
    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) ->
                (country == null || country.isBlank()) ? cb.conjunction() :
                        cb.equal(cb.lower(root.get("address").get("country")), country.toLowerCase());
    }

    /**
     * Creates specification for filtering hotels that contain
     * all specified amenities.
     * <p>
     * Uses a subquery with {@code GROUP BY} and {@code HAVING}
     * to ensure that each hotel contains every requested amenity,
     * not just any of them.
     *
     * @param amenities collection of required amenities
     * @return specification for filtering hotels by amenities
     */
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

    /**
     * Builds a combined specification using provided search parameters.
     *
     * @param name hotel name
     * @param brand hotel brand
     * @param city hotel city
     * @param country hotel country
     * @param amenities required amenities
     * @return combined hotel search specification
     */
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
