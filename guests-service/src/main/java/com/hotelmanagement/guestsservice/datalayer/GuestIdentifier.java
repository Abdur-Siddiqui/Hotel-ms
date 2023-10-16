package com.hotelmanagement.guestsservice.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;
@Embeddable
public class GuestIdentifier {

    private String guestId;

    public GuestIdentifier() {
        this.guestId = UUID.randomUUID().toString();
    }

    public String getGuestId(){
        return guestId;
    }
}
