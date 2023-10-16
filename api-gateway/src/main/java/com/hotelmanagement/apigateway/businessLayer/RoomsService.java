package com.hotelmanagement.apigateway.businessLayer;


import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsResponseModel;

public interface RoomsService {

    RoomsResponseModel getRoomByRoomNumber(Integer roomNumber);
   RoomsResponseModel[] getRooms();
    RoomsResponseModel addRoom(RoomsRequestModel roomsRequestModel);
    void updateRoom(RoomsRequestModel roomsRequestModel, Integer roomNumber);
    void deleteRoom(Integer roomNumber);
}
