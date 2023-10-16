package com.hotelmanagement.apigateway.presentationLayer.Reservation;
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

Payment payment;

LocalDate dateReservationmade;

Status roomStatus;


}
