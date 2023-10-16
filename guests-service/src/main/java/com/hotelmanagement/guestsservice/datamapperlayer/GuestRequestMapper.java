package com.hotelmanagement.guestsservice.datamapperlayer;


import com.hotelmanagement.guestsservice.datalayer.Guest;
import com.hotelmanagement.guestsservice.presentationlayer.GuestRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface GuestRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(guestIdentifier) ", target = "guestIdentifier", ignore = true)
    Guest requestModelToEntity(GuestRequestModel guestRequestModel);
}


