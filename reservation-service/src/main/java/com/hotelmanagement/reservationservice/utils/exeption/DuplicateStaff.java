package com.hotelmanagement.reservationservice.utils.exeption;

public class DuplicateStaff extends RuntimeException{

    public DuplicateStaff() {}

    public DuplicateStaff(String message) { super(message); }


}
