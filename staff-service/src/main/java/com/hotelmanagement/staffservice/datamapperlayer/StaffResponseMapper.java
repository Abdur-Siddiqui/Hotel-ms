package com.hotelmanagement.staffservice.datamapperlayer;


import com.hotelmanagement.staffservice.datalayer.StaffPersonal;
import com.hotelmanagement.staffservice.presentationlayer.StaffResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface StaffResponseMapper {
    @Mapping(expression = "java(staffPersonal.getStaffPersonalIdentifier().getStaffId())",
            target = "staffId")

    @Mapping(expression = "java(staffPersonal.getAddress().getCity())",
            target = "employeeCity")

    @Mapping(expression = "java(staffPersonal.getAddress().getPostalCode())",
            target = "employeePostalCode")
    StaffResponseModel entityToResponseModel(StaffPersonal staffPersonal);

    List<StaffResponseModel> entityListToResponseModelList(List<StaffPersonal> staffPersonal);


}
