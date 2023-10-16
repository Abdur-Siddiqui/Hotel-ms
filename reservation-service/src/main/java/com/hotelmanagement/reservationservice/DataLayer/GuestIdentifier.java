package com.hotelmanagement.reservationservice.DataLayer;



import java.util.UUID;

public class GuestIdentifier {

    private String guestId;

    public GuestIdentifier(String guestId) {
        this.guestId =guestId;
    }

    public String getGuestId(){
        return guestId;
    }
}
