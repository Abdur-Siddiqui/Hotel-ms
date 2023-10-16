package com.hotelmanagement.reservationservice.presenation;

import com.hotelmanagement.reservationservice.Buissnesslayer.ReservationService;
import com.hotelmanagement.reservationservice.utils.exeption.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/guests/{guestId}/reservations")
@RequiredArgsConstructor
public class GuestReservationController {
    private final ReservationService reservationService;

    private Integer UUID_SIZE = 36;



    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseModel> getReservationByGuestId(@PathVariable String guestId, @PathVariable String reservationId){
        if (guestId.length() != UUID_SIZE){
            throw new NotFoundException("Invalid guest idprovided: " + guestId);
        }
        if (reservationId.length() != UUID_SIZE){
            throw new NotFoundException("Invalid reservation id provided: " + reservationId);
        }
        return ResponseEntity.ok().body(reservationService.getReservationByGuestId(guestId, reservationId));
    }
    @PostMapping()
    public ResponseEntity<ReservationResponseModel> createReservationForGuest(@RequestBody ReservationRequestModel reservationRequestModel, @PathVariable String guestId){
        if (guestId.length() != UUID_SIZE){
            throw new NotFoundException("Invalid guest idprovided: " + guestId);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservationForGuest(reservationRequestModel, guestId));
    }
    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseModel> updateReservationForGuest(@RequestBody ReservationRequestModel reservationRequestModel, @PathVariable String guestId, @PathVariable String reservationId){
        if (guestId.length() != UUID_SIZE){
            throw new NotFoundException("Invalid guest idprovided: " + guestId);
        }
        if (reservationId.length() != UUID_SIZE){
            throw new NotFoundException("Invalid reservation id provided: " + reservationId);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.updateReservationForGuest(reservationRequestModel, guestId, reservationId));
    }
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservationForGuest(@PathVariable String guestId, @PathVariable String reservationId){
        if (guestId.length() != UUID_SIZE){
            throw new NotFoundException("Invalid guest id provided: " + guestId);
        }
        if (reservationId.length() != UUID_SIZE){
            throw new NotFoundException("Invalid reservation id provided: " + reservationId);
        }
        reservationService.deleteReservationForGuest(guestId, reservationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}



