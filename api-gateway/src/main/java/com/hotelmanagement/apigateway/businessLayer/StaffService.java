package com.hotelmanagement.apigateway.businessLayer;


import com.hotelmanagement.apigateway.presentationLayer.Staff.StaffRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Staff.StaffResponseModel;

public interface StaffService {

    StaffResponseModel getStaffById(String staffId);
    StaffResponseModel[] getStaffMembers();
    StaffResponseModel addStaff(StaffRequestModel staffRequestModel);
    void updateStaffPersonel(StaffRequestModel staffRequestModel, String staffId);
    void deleteStaffPersonel(String staffId);
}
