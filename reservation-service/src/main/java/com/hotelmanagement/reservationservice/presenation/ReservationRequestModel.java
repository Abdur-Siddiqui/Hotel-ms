package com.hotelmanagement.reservationservice.presenation;

import com.hotelmanagement.reservationservice.DataLayer.Payment;
import com.hotelmanagement.reservationservice.DataLayer.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor
public class ReservationRequestModel {

String roomId;

String staffId;


Integer roomNumber;

Integer price;

//Payment payment;

LocalDate dateReservationmade;

Status roomStatus;


}
