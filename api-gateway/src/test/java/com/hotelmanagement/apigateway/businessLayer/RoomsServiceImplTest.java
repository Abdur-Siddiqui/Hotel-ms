package com.hotelmanagement.apigateway.businessLayer;

import static org.junit.jupiter.api.Assertions.*;

import com.hotelmanagement.apigateway.domainClientLayer.RoomServiceClient;
import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
public class RoomsServiceImplTest {



        private RoomServiceClient mockClient;
        private RoomsServiceImpl roomsService;

        @BeforeEach
        void setUp() {
            // Create a mock instance of RoomServiceClient
            mockClient = mock(RoomServiceClient.class);
            // Create an instance of RoomsServiceImpl using the mock client
            roomsService = new RoomsServiceImpl(mockClient);
        }

        @Test
        void getRoomByRoomNumber_ShouldCallClientGetRoomByRoomNumber() {
            int roomNumber = 123;

            // Call the method to be tested
            roomsService.getRoomByRoomNumber(roomNumber);

            // Verify that the client's getRoomByRoomNumber method was called with the correct argument
            verify(mockClient).getRoomByRoomNumber(roomNumber);
        }

        @Test
        void getRooms_ShouldCallClientGetRooms() {
            // Call the method to be tested
            roomsService.getRooms();

            // Verify that the client's getRooms method was called
            verify(mockClient).getRooms();
        }

        @Test
        void addRoom_ShouldCallClientAddRoom() {

            String roomType = "Twin";
            Integer roomSize = 340;
            Integer roomNumber = 76;
            Integer price = 180;


            RoomsRequestModel roomsRequestModel = new RoomsRequestModel( roomType, roomSize, roomNumber, price);


            // Call the method to be tested
            roomsService.addRoom(roomsRequestModel);

            // Verify that the client's addRoom method was called with the correct argument
            verify(mockClient).addRoom(roomsRequestModel);
        }

        @Test
        void updateRoom_ShouldCallClientUpdateRoom() {
            // Create a RoomsRequestModel for testing
            String roomType = "Twin";
            Integer roomSize = 340;
            Integer roomNumber = 376;
            Integer price = 180;


            RoomsRequestModel roomsRequestModel = new RoomsRequestModel( roomType, roomSize, roomNumber, price);



            // Call the method to be tested
            roomsService.updateRoom(roomsRequestModel, roomNumber);

            // Verify that the client's updateRoom method was called with the correct arguments
            verify(mockClient).updateRoom(roomsRequestModel, roomNumber);
        }

        @Test
        void deleteRoom_ShouldCallClientDeleteRoom() {
            int roomNumber = 123;

            // Call the method to be tested
            roomsService.deleteRoom(roomNumber);

            // Verify that the client's deleteRoom method was called with the correct argument
            verify(mockClient).deleteRoom(roomNumber);
        }
    }


