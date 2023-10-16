package com.hotelmanagement.roomsservice.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;
@Embeddable
public class RoomIdentifier {

    private String roomId;

    public RoomIdentifier() {
        this.roomId = UUID.randomUUID().toString();
    }

    public String getRoomId(){
        return roomId;
    }
}
