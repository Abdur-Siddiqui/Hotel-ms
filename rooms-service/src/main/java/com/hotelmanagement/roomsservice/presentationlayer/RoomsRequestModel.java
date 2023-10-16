package com.hotelmanagement.roomsservice.presentationlayer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


public class RoomsRequestModel {
    //private String roomId;
    private String roomType;
    private Integer roomSize;
    private Integer roomNumber;
    private Integer price;

    public RoomsRequestModel() {
    }

   // public RoomsRequestModel(String roomId, String roomType, Integer roomSize, Integer roomNumber, Integer price) {
     //   this.roomId = roomId;
       // this.roomType = roomType;
       // this.roomSize = roomSize;
       // this.roomNumber = roomNumber;
       // this.price = price;
   // }

    public RoomsRequestModel(Integer roomNumber, String roomType, Integer roomSize, Integer price) {
   this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomSize = roomSize;
        this.price = price;
    }
/*
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

 */

    public String getRoomType() {
        return roomType;
    }

  //  public void setRoomType(String roomType) {
    //    this.roomType = roomType;
    //}

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(Integer roomSize) {
        this.roomSize = roomSize;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
