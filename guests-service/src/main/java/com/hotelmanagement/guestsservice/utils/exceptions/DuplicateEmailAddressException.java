package com.hotelmanagement.guestsservice.utils.exceptions;

public class DuplicateEmailAddressException extends  RuntimeException {
    public DuplicateEmailAddressException(String message) {
        super(message);
    }
}
