package com.hotelmanagement.apigateway.presentationLayer.Rooms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RoomsRequestModel {
    // String roomId;
     String roomType;
     Integer roomSize;
     Integer roomNumber;
     Integer price;


}
