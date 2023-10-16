package com.hotelmanagement.guestsservice.datalayer;


import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name="guests")
public class Guest {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id; // private id
   @Embedded
   private GuestIdentifier guestIdentifier; // public id

   private String firstName;
   private String lastName;
   @Column(unique = true)
   private String emailAddress;

   @Enumerated(EnumType.STRING)
   private Payment payment;

   //private String paymentMethod;
;
   @Embedded
   private Address address;





   public Guest() {
      this.guestIdentifier = new GuestIdentifier();
   }

   public Guest(String firstName, String lastName, String emailAddress, Address address) {
      this.guestIdentifier = new GuestIdentifier();
      this.firstName = firstName;
      this.lastName = lastName;
      this.emailAddress = emailAddress;
      this.address = address;


   }





}
