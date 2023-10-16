package com.hotelmanagement.reservationservice.presenation;

import com.hotelmanagement.reservationservice.Buissnesslayer.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping()
    ResponseEntity<List<ReservationResponseModel>> getAllReservations(){
        return ResponseEntity.ok().body(reservationService.getAllReservations());
    }
}
