package com.hotelmanagement.reservationservice.DomainClientLayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;


@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)

public class StaffResponseModel extends RepresentationModel<StaffResponseModel> {

      String staffId;

     String employeeFirstName;
     String employeeLastName;
     String employeeEmailAddress;


     String employeePhoneNumber;


     String employeeJobPosition;

     String  reservationDateMade;



     String employeeCity;

     String employeePostalCode;



}


