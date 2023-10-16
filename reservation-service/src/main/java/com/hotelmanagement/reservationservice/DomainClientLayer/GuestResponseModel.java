package com.hotelmanagement.reservationservice.DomainClientLayer;

import com.hotelmanagement.reservationservice.DataLayer.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class GuestResponseModel extends RepresentationModel<GuestResponseModel> {



     String guestId;

     String firstName;
     String lastName;
     String emailAddress;
     String streetAddress;
     String city;
     String province;
     String country;
     String postalCode;

   private Payment payment;
    }


