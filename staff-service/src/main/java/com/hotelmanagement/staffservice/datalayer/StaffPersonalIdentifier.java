package com.hotelmanagement.staffservice.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;
@Embeddable
public class StaffPersonalIdentifier {

    private String staffId;

    public StaffPersonalIdentifier() {
        this.staffId = UUID.randomUUID().toString();
    }

    public String getStaffId(){
        return staffId;
    }
}
