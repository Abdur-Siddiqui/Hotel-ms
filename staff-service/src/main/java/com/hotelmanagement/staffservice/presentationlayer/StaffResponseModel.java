package com.hotelmanagement.staffservice.presentationlayer;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
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


