package com.hotelmanagement.reservationservice.DataMapper;

import com.hotelmanagement.reservationservice.DataLayer.Reservation;
import com.hotelmanagement.reservationservice.presenation.ReservationResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ReservationResponseMapper {
    @Mapping(expression = "java(reservation.getReservationIdentifier().getReservationId())", target = "reservationId")
    @Mapping(expression = "java(reservation.getRoomIdentifier().getRoomNumber())", target = "roomNumber")
    @Mapping(expression = "java(reservation.getStaffPersonalIdentifier().getStaffId())", target = "staffId")
    @Mapping(expression = "java(reservation.getGuestIdentifier().getGuestId())", target = "guestId")
    ReservationResponseModel entityToResponseModel(Reservation reservation);

    List<ReservationResponseModel> entityListToResponseModelList(List<Reservation> reservations);
}

