package com.hotelmanagement.reservationservice.DomainClientLayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class RoomsResponseModel extends RepresentationModel<RoomsResponseModel> {





   String roomType;
   Integer roomSize;
  Integer roomNumber;
    Integer price;

}


