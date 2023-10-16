package com.hotelmanagement.roomsservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoomRepository extends JpaRepository <Room, Integer>{
        //Room findByRoomIdentifier_RoomId(String roomId);

        Room findRoomByRoomNumber(Integer roomNumber);



}
