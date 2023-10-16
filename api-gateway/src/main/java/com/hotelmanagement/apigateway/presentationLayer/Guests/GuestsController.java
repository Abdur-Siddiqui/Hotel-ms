package com.hotelmanagement.apigateway.presentationLayer.Guests;


import com.hotelmanagement.apigateway.Utils.Exceptions.InvalidInputException;
import com.hotelmanagement.apigateway.businessLayer.GuestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/guests")
public class GuestsController {

    private GuestsService guestsService;

    private final Integer UUID_SIZE = 36;

    public GuestsController(GuestsService guestsService) {
        this.guestsService = guestsService;
    }



    @GetMapping(value = "/{guestId}", produces = "application/json")
    ResponseEntity<GuestResponseModel> getGuestByGuestId(@PathVariable String guestId){
        if(guestId.length() != UUID_SIZE){
            throw new InvalidInputException("GuestId is invalid: " + guestId);
        }

        log.debug("1, Received in API_Gateway guests Controller getGuestVehicleAggregate with guestId");
        return ResponseEntity.ok().body(guestsService.getGuestByGuestId(guestId));
    }

    @GetMapping(
            produces = "application/json"
    )
    ResponseEntity<GuestResponseModel[]> getGuests(){
        log.debug("1, Received in API_Gateway guests Controller getGuests");
        return ResponseEntity.ok().body(guestsService.getGuests());
    }

    @PostMapping(
            produces = "application/json"
    )
    ResponseEntity<GuestResponseModel> addGuest(@RequestBody GuestRequestModel  guestRequestModel){
        log.debug("1, Received in API_Gateway Guests Controller addGuest");
        return ResponseEntity.status(HttpStatus.CREATED).body(guestsService.addGuest(guestRequestModel));
    }

    @PutMapping("/{guestId}")
    void updateGuest(@RequestBody GuestRequestModel guestRequestModel, @PathVariable String guestId){
        guestsService.updateGuest(guestRequestModel, guestId);
    }

    @DeleteMapping("/{guestId}")
    void deleteGuest(@PathVariable String guestId){
      guestsService.deleteGuest(guestId);
    }

}

