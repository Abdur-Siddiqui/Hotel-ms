package com.hotelmanagement.staffservice.utils.exceptions;

public class DuplicatePhoneNumberException extends  RuntimeException{

    public DuplicatePhoneNumberException(String message) {
        super(message);
    }


}
