package com.hotelmanagement.apigateway.presentationLayer.Reservation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReservationResponseModel {

    private String reservationId;

    private Status roomStatus;

    private String roomId;
    private Integer roomNumber;
    private Integer price;

    private String guestId;
    private String guestFirstName;
    private String guestLastName;
    private Payment payment;

    private String staffId;
    private String employeeFirstName;
    private String employeeLastName;

    private LocalDate reservationDateMade;


}
