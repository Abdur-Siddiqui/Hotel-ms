package com.hotelmanagement.staffservice.businesslayer;

import com.hotelmanagement.staffservice.presentationlayer.StaffRequestModel;
import com.hotelmanagement.staffservice.presentationlayer.StaffResponseModel;

import java.util.List;

public interface StaffService {

    List<StaffResponseModel> getStaff();
   // List<Guest> getguests();
    StaffResponseModel getStaffByStaffId(String staffId);
    StaffResponseModel updateStaff(StaffRequestModel staffRequestModel, String staffId);
    StaffResponseModel addStaff (StaffRequestModel staffRequestModel);

    void removeStaff(String staffId);
}
