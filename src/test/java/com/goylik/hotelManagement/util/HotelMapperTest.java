package com.goylik.hotelManagement.util;

import com.goylik.hotelManagement.model.dto.AddressDto;
import com.goylik.hotelManagement.model.dto.ArrivalTimeDto;
import com.goylik.hotelManagement.model.dto.ContactsDto;
import com.goylik.hotelManagement.model.dto.request.CreateHotelRequest;
import com.goylik.hotelManagement.model.dto.response.HotelResponse;
import com.goylik.hotelManagement.model.dto.response.HotelShortResponse;
import com.goylik.hotelManagement.model.entity.Amenity;
import com.goylik.hotelManagement.model.entity.Hotel;
import com.goylik.hotelManagement.model.entity.embedded.Address;
import com.goylik.hotelManagement.model.entity.embedded.ArrivalTime;
import com.goylik.hotelManagement.model.entity.embedded.Contacts;
import com.goylik.hotelManagement.util.mapper.HotelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HotelMapperTest {

    @Autowired
    private HotelMapper hotelMapper;

    private CreateHotelRequest createRequest;
    private Hotel hotel;
    private Address address;
    private Contacts contacts;
    private ArrivalTime arrivalTime;
    private List<Amenity> amenities;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setHouseNumber("9");
        address.setStreet("Pobediteley Avenue");
        address.setCity("Minsk");
        address.setCountry("Belarus");
        address.setPostCode("220004");

        contacts = new Contacts();
        contacts.setPhone("+375 17 309-80-00");
        contacts.setEmail("doubletreeminsk.info@hilton.com");

        arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(LocalTime.of(14, 0));
        arrivalTime.setCheckOut(LocalTime.of(12, 0));

        Amenity wifi = new Amenity();
        wifi.setId(1L);
        wifi.setName("Free WiFi");

        Amenity parking = new Amenity();
        parking.setId(2L);
        parking.setName("Free parking");

        amenities = Arrays.asList(wifi, parking);

        createRequest = new CreateHotelRequest(
                "DoubleTree by Hilton Minsk",
                "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms...",
                "Hilton",
                new AddressDto(
                        "9",
                        "Pobediteley Avenue",
                        "Minsk",
                        "Belarus",
                        "220004"
                ),
                new ContactsDto(
                        "+375 17 309-80-00",
                        "doubletreeminsk.info@hilton.com"
                ),
                new ArrivalTimeDto(
                        LocalTime.of(14, 0),
                        LocalTime.of(12, 0)
                )
        );

        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("DoubleTree by Hilton Minsk");
        hotel.setDescription("The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms...");
        hotel.setBrand("Hilton");
        hotel.setAddress(address);
        hotel.setContacts(contacts);
        hotel.setArrivalTime(arrivalTime);
        hotel.setAmenities(amenities);
    }

    @Test
    void toEntity_ShouldMapAllFieldsCorrectly() {
        Hotel result = hotelMapper.toEntity(createRequest);

        assertNotNull(result);
        assertEquals(createRequest.name(), result.getName());
        assertEquals(createRequest.description(), result.getDescription());
        assertEquals(createRequest.brand(), result.getBrand());

        assertNotNull(result.getAddress());
        assertEquals(createRequest.address().houseNumber(), result.getAddress().getHouseNumber());
        assertEquals(createRequest.address().street(), result.getAddress().getStreet());
        assertEquals(createRequest.address().city(), result.getAddress().getCity());
        assertEquals(createRequest.address().country(), result.getAddress().getCountry());
        assertEquals(createRequest.address().postCode(), result.getAddress().getPostCode());

        assertNotNull(result.getContacts());
        assertEquals(createRequest.contacts().phone(), result.getContacts().getPhone());
        assertEquals(createRequest.contacts().email(), result.getContacts().getEmail());

        assertNotNull(result.getArrivalTime());
        assertEquals(createRequest.arrivalTime().checkIn(), result.getArrivalTime().getCheckIn());
        assertEquals(createRequest.arrivalTime().checkOut(), result.getArrivalTime().getCheckOut());

        assertNull(result.getAmenities());
    }

    @Test
    void toEntity_ShouldHandleNullValues() {
        CreateHotelRequest nullRequest = new CreateHotelRequest(
                null, null, null, null, null, null
        );

        Hotel result = hotelMapper.toEntity(nullRequest);

        assertNotNull(result);
        assertNull(result.getName());
        assertNull(result.getDescription());
        assertNull(result.getBrand());
        assertNull(result.getAddress());
        assertNull(result.getContacts());
        assertNull(result.getArrivalTime());
    }

    @Test
    void toEntity_ShouldWorkWithOptionalFields() {
        CreateHotelRequest requestWithoutOptional = new CreateHotelRequest(
                "DoubleTree by Hilton Minsk",
                null,
                "Hilton",
                new AddressDto("9", "Pobediteley Avenue", "Minsk", "Belarus", "220004"),
                new ContactsDto("+375 17 309-80-00", "doubletreeminsk.info@hilton.com"),
                new ArrivalTimeDto(
                        LocalTime.of(14, 0),
                        null
                )
        );

        Hotel result = hotelMapper.toEntity(requestWithoutOptional);

        assertNotNull(result);
        assertEquals(requestWithoutOptional.name(), result.getName());
        assertNull(result.getDescription());
        assertEquals(requestWithoutOptional.brand(), result.getBrand());

        assertNotNull(result.getArrivalTime());
        assertEquals(requestWithoutOptional.arrivalTime().checkIn(), result.getArrivalTime().getCheckIn());
        assertNull(result.getArrivalTime().getCheckOut());
    }

    @Test
    void toResponse_ShouldMapAllFieldsCorrectly() {
        HotelResponse result = hotelMapper.toResponse(hotel);

        assertNotNull(result);
        assertEquals(hotel.getId(), result.id());
        assertEquals(hotel.getName(), result.name());
        assertEquals(hotel.getDescription(), result.description());
        assertEquals(hotel.getBrand(), result.brand());

        assertNotNull(result.address());
        assertEquals(hotel.getAddress().getHouseNumber(), result.address().houseNumber());
        assertEquals(hotel.getAddress().getStreet(), result.address().street());
        assertEquals(hotel.getAddress().getCity(), result.address().city());
        assertEquals(hotel.getAddress().getCountry(), result.address().country());
        assertEquals(hotel.getAddress().getPostCode(), result.address().postCode());

        assertNotNull(result.contacts());
        assertEquals(hotel.getContacts().getPhone(), result.contacts().phone());
        assertEquals(hotel.getContacts().getEmail(), result.contacts().email());

        assertNotNull(result.arrivalTime());
        assertEquals(hotel.getArrivalTime().getCheckIn(), result.arrivalTime().checkIn());
        assertEquals(hotel.getArrivalTime().getCheckOut(), result.arrivalTime().checkOut());

        assertNotNull(result.amenities());
        assertEquals(2, result.amenities().size());
        assertEquals("Free WiFi", result.amenities().get(0).name());
        assertEquals("Free parking", result.amenities().get(1).name());
    }

    @Test
    void toResponse_ShouldHandleNullValues() {
        hotel.setAddress(null);
        hotel.setContacts(null);
        hotel.setArrivalTime(null);
        hotel.setAmenities(null);

        HotelResponse result = hotelMapper.toResponse(hotel);

        assertNotNull(result);
        assertEquals(hotel.getId(), result.id());
        assertEquals(hotel.getName(), result.name());
        assertNull(result.address());
        assertNull(result.contacts());
        assertNull(result.arrivalTime());
        assertNull(result.amenities());
    }

    @Test
    void toResponse_ShouldWorkWithEmptyAmenities() {
        hotel.setAmenities(List.of());

        HotelResponse result = hotelMapper.toResponse(hotel);

        assertNotNull(result);
        assertNotNull(result.amenities());
        assertTrue(result.amenities().isEmpty());
    }

    @Test
    void toShortResponse_ShouldMapAllFieldsCorrectly() {
        HotelShortResponse result = hotelMapper.toShortResponse(hotel);

        assertNotNull(result);
        assertEquals(hotel.getId(), result.id());
        assertEquals(hotel.getName(), result.name());
        assertEquals(hotel.getDescription(), result.description());

        String expectedAddress = address.toString();
        assertEquals(expectedAddress, result.address());

        assertEquals(hotel.getContacts().getPhone(), result.phone());
    }

    @Test
    void addressToString_ShouldFormatAddressCorrectly() {
        String result = hotelMapper.addressToString(address);

        String expected = "9 Pobediteley Avenue, Minsk, 220004, Belarus";
        assertEquals(expected, result);
    }

    @Test
    void fullCycle_ShouldPreserveData() {
        CreateHotelRequest request = createRequest;

        Hotel hotel = hotelMapper.toEntity(request);
        HotelResponse response = hotelMapper.toResponse(hotel);

        assertNotNull(hotel);
        assertNotNull(response);

        assertEquals(request.name(), response.name());
        assertEquals(request.description(), response.description());
        assertEquals(request.brand(), response.brand());

        assertEquals(request.address().houseNumber(), response.address().houseNumber());
        assertEquals(request.address().street(), response.address().street());
        assertEquals(request.address().city(), response.address().city());
        assertEquals(request.address().country(), response.address().country());
        assertEquals(request.address().postCode(), response.address().postCode());

        assertEquals(request.contacts().phone(), response.contacts().phone());
        assertEquals(request.contacts().email(), response.contacts().email());

        assertEquals(request.arrivalTime().checkIn(), response.arrivalTime().checkIn());
        assertEquals(request.arrivalTime().checkOut(), response.arrivalTime().checkOut());
    }

    @Test
    void fullCycleWithShortResponse_ShouldWork() {
        CreateHotelRequest request = createRequest;

        Hotel hotel = hotelMapper.toEntity(request);
        HotelShortResponse shortResponse = hotelMapper.toShortResponse(hotel);

        assertNotNull(hotel);
        assertNotNull(shortResponse);

        assertEquals(request.name(), shortResponse.name());
        assertEquals(request.description(), shortResponse.description());
        assertTrue(shortResponse.address().contains(request.address().houseNumber()));
        assertTrue(shortResponse.address().contains(request.address().city()));
        assertEquals(request.contacts().phone(), shortResponse.phone());
    }
}
