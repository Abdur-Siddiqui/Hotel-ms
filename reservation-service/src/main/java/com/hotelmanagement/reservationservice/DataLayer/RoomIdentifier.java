package com.hotelmanagement.reservationservice.DataLayer;



import java.util.UUID;

public class RoomIdentifier {

    private Integer roomNumber;

    public RoomIdentifier(Integer roomNumber) {
        this.roomNumber = roomNumber ;
    }

    public Integer getRoomNumber(){
        return roomNumber;
    }
}