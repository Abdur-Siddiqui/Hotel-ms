package com.hotelmanagement.roomsservice.datamapperlayer;


import com.hotelmanagement.roomsservice.datalayer.Room;
import com.hotelmanagement.roomsservice.presentationlayer.RoomsResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface RoomResponseMapper {
    @Mapping(expression = "java(room.getRoomIdentifier().getRoomId())",
            target = "roomId")
    RoomsResponseModel entityToResponseModel(Room room);

    List<RoomsResponseModel> entityListToResponseModelList(List<Room> rooms) ;


}
