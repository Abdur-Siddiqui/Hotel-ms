package com.hotelmanagement.apigateway.presentationLayer.Reservation;
import com.hotelmanagement.apigateway.businessLayer.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/guests/{guestId}/reservations")
@RequiredArgsConstructor
public class GuestsReservationController {
    private final ReservationService reservationService;

    @GetMapping(value = "/{reservationId}", produces = "application/json")
    ResponseEntity<ReservationResponseModel> getGuestReservationByGuestId(@PathVariable String guestId, @PathVariable String reservationId){
        return ResponseEntity.ok().body(reservationService.getGuestReservationByGuestId(guestId, reservationId));
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<ReservationResponseModel> addGuestsReservation(@RequestBody ReservationRequestModel reservationRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.addGuestsReservation(reservationRequestModel));
    }


    @PutMapping("/{reservationId}")
    void updateGuestReservation(@RequestBody ReservationRequestModel reservationRequestModel, @PathVariable String guestId, @PathVariable String reservationId){
        reservationService.updateGuestReservation(reservationRequestModel, guestId, reservationId);
    }


    @DeleteMapping("/{reservationId}")
    void deleteGuest(@PathVariable String guestId, @PathVariable String reservationId){
        reservationService.deleteGuestReservation(guestId, reservationId);
    }

    @GetMapping(
            produces = "application/json"
    )
    ResponseEntity<ReservationResponseModel[]> getAllReservation(){
        return ResponseEntity.ok().body(reservationService.getAllReservation());
    }

}
