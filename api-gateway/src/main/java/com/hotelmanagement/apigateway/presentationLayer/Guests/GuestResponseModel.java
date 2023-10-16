package com.hotelmanagement.apigateway.presentationLayer.Guests;

import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsResponseModel;
import lombok.*;
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

    Payment payment;
    }


