package com.hotelmanagement.roomsservice.datamapperlayer;


import com.hotelmanagement.roomsservice.datalayer.Room;
import com.hotelmanagement.roomsservice.presentationlayer.RoomsRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RoomRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(roomIdentifier) ", target = "roomIdentifier", ignore = true)
    Room requestModelToEntity(RoomsRequestModel roomsRequestModel);
}


