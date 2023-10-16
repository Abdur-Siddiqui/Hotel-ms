package com.hotelmanagement.roomsservice.businesslayer;

import com.hotelmanagement.roomsservice.presentationlayer.RoomsRequestModel;
import com.hotelmanagement.roomsservice.presentationlayer.RoomsResponseModel;

import java.util.List;

public interface RoomService {

    List<RoomsResponseModel> getRooms();
   // List<Rooms> getroomss();
    //RoomsResponseModel getRoomByRoomId(String roomsId);

    RoomsResponseModel getRoomByRoomNumber(Integer roomNumber);
    RoomsResponseModel updateRoom(RoomsRequestModel roomsRequestModel, Integer roomNumber);
    RoomsResponseModel addRoom(RoomsRequestModel roomsRequestModel);

    void removeRooms(Integer roomNumber);
}
