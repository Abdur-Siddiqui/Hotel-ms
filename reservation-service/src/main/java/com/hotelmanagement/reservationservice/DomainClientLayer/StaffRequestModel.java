package com.hotelmanagement.reservationservice.DomainClientLayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)


public class StaffRequestModel {




     String employeeFirstName;
     String employeeLastName;
     String employeeEmailAddress;


    String employeePhoneNumber;


     String employeeJobPosition;

     String  reservationDateMade;




     String employeeCity;

     String employeePostalCode;



}
