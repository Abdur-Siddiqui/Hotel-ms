package com.hotelmanagement.guestsservice.datamapperlayer;

import com.hotelmanagement.guestsservice.datalayer.Guest;
import com.hotelmanagement.guestsservice.presentationlayer.GuestResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface GuestResponseMapper {
    @Mapping(expression = "java(guest.getGuestIdentifier().getGuestId())",
            target = "guestId")

    @Mapping(expression = "java(guest.getAddress().getStreetAddress())",
            target = "streetAddress")
    @Mapping(expression = "java(guest.getAddress().getCity())",
            target = "city")
    @Mapping(expression = "java(guest.getAddress().getProvince())",
            target = "province")
    @Mapping(expression = "java(guest.getAddress().getCountry())",
            target = "country")
    @Mapping(expression = "java(guest.getAddress().getPostalCode())",
            target = "postalCode")
    GuestResponseModel entityToResponseModel(Guest guest);

    List<GuestResponseModel> entityListToResponseModelList(List<Guest> guests ) ;


}
