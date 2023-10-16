package com.hotelmanagement.reservationservice.DomainClientLayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class GuestRequestModel {


    String firstName;
    String lastName;
    String emailAddress;
    String streetAddress;
    String city;
    String province;
    String country;
    String postalCode;


    // String paymentMethod;
}
