package com.hotelmanagement.reservationservice.DataLayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Document(collection = "reservations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {



        @Id
        private String id;

        private ReservationIdentifier reservationIdentifier;
        private RoomIdentifier roomIdentifier;
        private GuestIdentifier guestIdentifier;
        private StaffPersonalIdentifier staffPersonalIdentifier;
        private Integer roomNumber;
        private Integer price;
        private String employeeFirstName;
        private String employeeLastName;
        private String guestFirstName;
        private String guestLastName;
        private LocalDate reservationDateMade;
        private Status roomStatus;





    }


