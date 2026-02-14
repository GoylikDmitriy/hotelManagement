package com.goylik.hotelManagement.exception;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException() {
        super();
    }

    public HotelNotFoundException(Long id) {
        super(String.format("Hotel with id = %d not found", id));
    }

    public HotelNotFoundException(String message) {
        super(message);
    }

    public HotelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HotelNotFoundException(Throwable cause) {
        super(cause);
    }
}
