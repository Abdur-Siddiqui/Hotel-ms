package com.hotelmanagement.reservationservice.utils;

import com.hotelmanagement.reservationservice.DataLayer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseLoaderService implements CommandLineRunner {

    @Autowired
    ReservationRepository reservationRepository;


    @Override
    public void run(String... args) throws Exception {

        var reservationIdentifier1 = new  ReservationIdentifier();
        var guestIdentifier1 = new GuestIdentifier("dd1ab8b0-ab17-4e03-b70a-84caa3871606");
        var staffPersonalIdentifier1 = new StaffPersonalIdentifier("c3540a89-cb47-4c96-888e-ff96708db4d8");
        var roomIdentifier1 = new RoomIdentifier( 256);


        var reservation1 = Reservation.builder()
                .reservationIdentifier(reservationIdentifier1)
                .guestIdentifier(guestIdentifier1)
                .staffPersonalIdentifier(staffPersonalIdentifier1)
                .roomIdentifier(roomIdentifier1)
                .employeeFirstName("Dorry")
                .employeeLastName("Gepp")
                .guestFirstName("Alick")
                .guestLastName("Ucceli")
                .roomStatus(Status.AVAILABLE)
                .price(150)
                .reservationDateMade(LocalDate.of(2023, 03, 10))
                .build();





        reservationRepository.insert(reservation1);

    }

}

