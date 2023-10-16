package com.hotelmanagement.reservationservice.Buissnesslayer;

import com.hotelmanagement.reservationservice.presenation.ReservationRequestModel;
import com.hotelmanagement.reservationservice.presenation.ReservationResponseModel;

import java.util.List;

public interface ReservationService {

    List<ReservationResponseModel> getAllReservations();
    ReservationResponseModel createReservationForGuest(ReservationRequestModel reservationRequestModel, String guestId);

    ReservationResponseModel getReservationByGuestId( String guestId, String reservationId);
    ReservationResponseModel updateReservationForGuest(ReservationRequestModel reservationRequestModel,String guestId, String reservationId);


    void  deleteReservationForGuest( String guestId,String reservationId);
}
