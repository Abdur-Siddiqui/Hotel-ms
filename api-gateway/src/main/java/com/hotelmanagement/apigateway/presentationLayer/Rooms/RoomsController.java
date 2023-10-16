package com.hotelmanagement.apigateway.presentationLayer.Rooms;

import com.hotelmanagement.apigateway.Utils.Exceptions.InvalidInputException;
import com.hotelmanagement.apigateway.businessLayer.RoomsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/rooms")
public class RoomsController {

    private RoomsService roomsService;



    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }



    @GetMapping(value = "/{roomNumber}", produces = "application/json")
    ResponseEntity<RoomsResponseModel> getRoomByRoomNumber(@PathVariable Integer roomNumber){

        log.debug("1, Received in API_Gateway rooms Controller getRoomVehicleAggregate with guestId");
        return ResponseEntity.ok().body(roomsService.getRoomByRoomNumber(roomNumber));
    }

    @GetMapping(
            produces = "application/json"
    )
    ResponseEntity<RoomsResponseModel[]> getRooms(){
        log.debug("1, Received in API_Gateway rooms Controller getRooms");
        return ResponseEntity.ok().body(roomsService.getRooms());
    }

    @PostMapping(
            produces = "application/json"
    )
    ResponseEntity<RoomsResponseModel> addRoom(@RequestBody RoomsRequestModel  roomsRequestModel){
        log.debug("1, Received in API_Gateway Rooms Controller addRoom");
        return ResponseEntity.status(HttpStatus.CREATED).body(roomsService.addRoom(roomsRequestModel));
    }

    @PutMapping("/{roomNumber}")
    void updateRoom(@RequestBody RoomsRequestModel roomsRequestModel, @PathVariable Integer roomNumber){
        roomsService.updateRoom(roomsRequestModel, roomNumber);
    }

    @DeleteMapping("/{roomNumber}")
    void deleteRoom(@PathVariable Integer roomNumber){
        roomsService.deleteRoom(roomNumber);
    }

}
