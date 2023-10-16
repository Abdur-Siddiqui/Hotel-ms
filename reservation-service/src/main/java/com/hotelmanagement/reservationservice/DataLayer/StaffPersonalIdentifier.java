package com.hotelmanagement.reservationservice.DataLayer;


import java.util.UUID;

public class StaffPersonalIdentifier {

    private String staffId;

    public StaffPersonalIdentifier(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId(){
        return staffId;
    }
}
