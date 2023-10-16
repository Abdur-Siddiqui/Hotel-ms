package com.hotelmanagement.roomsservice.presentationlayer;


import com.hotelmanagement.roomsservice.businesslayer.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public List<RoomsResponseModel> getRooms(){
        return roomService.getRooms();
    }
    @GetMapping("/{roomNumber}")
    public  ResponseEntity<RoomsResponseModel> getRoomByRoomNumber(@PathVariable Integer roomNumber){
        return ResponseEntity.ok().body(roomService.getRoomByRoomNumber(roomNumber));
    }

    @PostMapping()
    ResponseEntity <RoomsResponseModel> addRoom(@RequestBody RoomsRequestModel roomsRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.addRoom(roomsRequestModel));
    }

    @PutMapping("/{roomNumber}")
    ResponseEntity <RoomsResponseModel> updateRoom(@RequestBody RoomsRequestModel roomsRequestModel, @PathVariable Integer roomNumber){
        return ResponseEntity.ok().body(roomService.updateRoom(roomsRequestModel, roomNumber));
    }

    @DeleteMapping("/{roomNumber}")
    ResponseEntity <Void> removeRoom(@PathVariable Integer roomNumber){

        roomService.removeRooms(roomNumber);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
