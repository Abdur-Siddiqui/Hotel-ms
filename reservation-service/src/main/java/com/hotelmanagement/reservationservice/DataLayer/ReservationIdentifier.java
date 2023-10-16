package com.hotelmanagement.reservationservice.DataLayer;

import java.util.UUID;

public class ReservationIdentifier {

    public String reservationId;

    public ReservationIdentifier() {
        this.reservationId = UUID.randomUUID().toString();
    }

    public String getReservationId() {
        return reservationId;
    }
}
