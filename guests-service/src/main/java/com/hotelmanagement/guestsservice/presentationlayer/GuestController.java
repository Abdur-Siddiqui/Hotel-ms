package com.hotelmanagement.guestsservice.presentationlayer;


import com.hotelmanagement.guestsservice.businesslayer.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping()
    public List<GuestResponseModel> getGuests(){
        return guestService.getGuests();
    }
    @GetMapping("/{guestId}")
    public ResponseEntity <GuestResponseModel> getGuestByGuestId(@PathVariable String guestId){
        return ResponseEntity.ok().body(guestService.getGuestByGuestId(guestId));
    }

    @PostMapping()
    ResponseEntity <GuestResponseModel> addGuest(@RequestBody GuestRequestModel guestRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(guestService.addGuest(guestRequestModel));
    }

    @PutMapping("/{guestId}")
    ResponseEntity <GuestResponseModel>updateGuest(@RequestBody GuestRequestModel guestRequestModel, @PathVariable String guestId){
        return ResponseEntity.ok().body( guestService.updateGuest(guestRequestModel, guestId));
    }

    @DeleteMapping("/{guestId}")
    ResponseEntity <Void> removeGuest(@PathVariable String guestId){
        guestService.removeGuest(guestId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

