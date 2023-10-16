package com.hotelmanagement.apigateway.businessLayer;

import com.hotelmanagement.apigateway.presentationLayer.Reservation.ReservationRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Reservation.ReservationResponseModel;

import java.util.List;

public interface ReservationService {

    ReservationResponseModel addGuestsReservation( ReservationRequestModel reservationRequestModel);
    ReservationResponseModel getGuestReservationByGuestId(String guestId, String reservationId);
    ReservationResponseModel[] getAllReservation();
    void updateGuestReservation( ReservationRequestModel reservationRequestModel,String guestId ,String reservationId);
    void deleteGuestReservation(String guestId, String reservationId);
}
