package com.hotelmanagement.roomsservice.datalayer;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="rooms")
public class Room {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id; // private id
   @Embedded
   private RoomIdentifier roomIdentifier; // public id

   private String roomType;
   private Integer roomSize;
   @Column(unique = true)
   private Integer roomNumber;

   private Integer price;



   public Room() {
      this.roomIdentifier = new RoomIdentifier();
   }

   public Room(  String roomType, Integer roomSize, Integer roomNumber, Integer price) {
      this.roomIdentifier = new RoomIdentifier();
      this.roomType = roomType;
      this.roomSize = roomSize;
      this.roomNumber = roomNumber;
      this.price = price;

   }
}
