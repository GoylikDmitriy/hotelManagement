package com.goylik.hotelManagement.exception;

public class UnsupportedHistogramParamException extends RuntimeException {
    public UnsupportedHistogramParamException() {
        super();
    }

    public UnsupportedHistogramParamException(String param) {
        super(String.format("The \"%s\" is not supported param for histogram.", param));
    }

    public UnsupportedHistogramParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedHistogramParamException(Throwable cause) {
        super(cause);
    }
}
