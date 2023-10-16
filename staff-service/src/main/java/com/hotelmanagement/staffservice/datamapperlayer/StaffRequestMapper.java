package com.hotelmanagement.staffservice.datamapperlayer;


import com.hotelmanagement.staffservice.datalayer.StaffPersonal;
import com.hotelmanagement.staffservice.presentationlayer.StaffRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface StaffRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(staffPersonalIdentifier) ", target = "staffPersonalIdentifier", ignore = true)
    StaffPersonal requestModelToEntity(StaffRequestModel staffRequestModel);
}


