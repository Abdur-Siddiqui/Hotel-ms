package com.hotelmanagement.staffservice.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="staff")
public class StaffPersonal {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id; // private id
   @Embedded
   private StaffPersonalIdentifier staffPersonalIdentifier; // public id

   private String employeeFirstName;
   private String employeeLastName;
   private String employeeEmailAddress;

   @Column(unique = true)
   private String employeePhoneNumber;


    private String employeeJobPosition;

   private String  reservationDateMade;

   @Embedded
   private Address address;



   public StaffPersonal() {
      this.staffPersonalIdentifier = new StaffPersonalIdentifier();
   }

   // ask prof qeustion about reservation model


   public StaffPersonal( String employeeFirstName, String employeeLastName, String employeeEmailAddress, String employeePhoneNumber, String employeeJobPosition, String reservationDateMade, Address address) {
      this.staffPersonalIdentifier = new StaffPersonalIdentifier();
      this.employeeFirstName = employeeFirstName;
      this.employeeLastName = employeeLastName;
      this.employeeEmailAddress = employeeEmailAddress;
      this.employeePhoneNumber = employeePhoneNumber;
      this.employeeJobPosition = employeeJobPosition;
      this.reservationDateMade = reservationDateMade;
      this.address = address;
   }


}
