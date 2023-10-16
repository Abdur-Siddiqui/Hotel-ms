package com.hotelmanagement.apigateway.businessLayer;

import com.hotelmanagement.apigateway.domainClientLayer.RoomServiceClient;
import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomsServiceImpl implements RoomsService {

  RoomServiceClient roomServiceClient;

    public RoomsServiceImpl(RoomServiceClient roomServiceClient) {
        this.roomServiceClient = roomServiceClient;
    }

    @Override
    public RoomsResponseModel getRoomByRoomNumber(Integer roomNumber) {
        return roomServiceClient.getRoomByRoomNumber(roomNumber);
    }

    @Override
    public RoomsResponseModel[] getRooms() {
        log.debug("2. Received in API-Gateway Room Service Impl getRooms");
        return roomServiceClient.getRooms();
    }

    @Override
    public RoomsResponseModel addRoom(RoomsRequestModel roomsRequestModel) {
        log.debug("2. Received in API-Gateway Inventory Service Impl addInventory");
        return roomServiceClient.addRoom(roomsRequestModel);
    }

    @Override
    public void updateRoom(RoomsRequestModel roomsRequestModel, Integer roomNumber) {
        roomServiceClient.updateRoom(roomsRequestModel, roomNumber);
    }

    @Override
    public void deleteRoom(Integer roomNumber) {
        roomServiceClient.deleteRoom(roomNumber);

    }
}
