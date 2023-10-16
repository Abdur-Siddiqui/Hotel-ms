package com.hotelmanagement.reservationservice.DataLayer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
    Reservation findByReservationIdentifier_ReservationId(String reservationId);



    Reservation findByGuestIdentifier_GuestIdAndAndReservationIdentifier_ReservationId(String guestId, String reservationId);
}
