package com.hotelmanagement.apigateway.businessLayer;

import com.hotelmanagement.apigateway.domainClientLayer.ReservationServiceCleint;
import com.hotelmanagement.apigateway.presentationLayer.Reservation.ReservationRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Reservation.ReservationResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RservationServiceImpl implements  ReservationService {

    private ReservationServiceCleint reservationServiceCleint;

    public RservationServiceImpl(ReservationServiceCleint reservationServiceCleint) {
        this.reservationServiceCleint = reservationServiceCleint;
    }

    @Override
    public ReservationResponseModel addGuestsReservation(ReservationRequestModel reservationRequestModel) {
        return reservationServiceCleint.addGuestsReservation(reservationRequestModel);
    }

    @Override
    public ReservationResponseModel getGuestReservationByGuestId(String guestId, String reservationId) {
        return reservationServiceCleint.getGuestReservationByReservationId(guestId, reservationId);
    }

    @Override
    public ReservationResponseModel[] getAllReservation() {
        return reservationServiceCleint.getAllReservations();
    }

    @Override
    public void updateGuestReservation(ReservationRequestModel reservationRequestModel, String guestId, String reservationId) {
        reservationServiceCleint.updateGuestReservation(reservationRequestModel, guestId, reservationId);
    }

    @Override
    public void deleteGuestReservation(String guestId, String reservationId) {
        reservationServiceCleint.delete(guestId, reservationId);

    }
}
