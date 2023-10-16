package com.hotelmanagement.apigateway.businessLayer;

import com.hotelmanagement.apigateway.domainClientLayer.StaffServiceClient;
import com.hotelmanagement.apigateway.presentationLayer.Staff.StaffRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Staff.StaffResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    private final StaffServiceClient staffServiceClient;

    public StaffServiceImpl(StaffServiceClient staffServiceClient) {
        this.staffServiceClient = staffServiceClient;
    }

    @Override
    public StaffResponseModel getStaffById(String staffId) {
        log.debug("2. Received in API-Gateway Inventory Service Impl getInventoryAggregate with inventoryId");
        return staffServiceClient.getStaffById(staffId);
    }

    @Override
    public StaffResponseModel[] getStaffMembers() {
        log.debug("2. Received in API-Gateway Inventory Service Impl getStaffMembers");
        return staffServiceClient.getStaffMembers();
    }

    @Override
    public StaffResponseModel addStaff(StaffRequestModel staffRequestModel) {
        log.debug("2. Received in API-Gateway Inventory Service Impl addInventory");
        return staffServiceClient.addStaff(staffRequestModel);
    }

    @Override
    public void updateStaffPersonel(StaffRequestModel staffRequestModel, String staffId) {
        staffServiceClient.updateStaffPersonel(staffRequestModel, staffId);
    }

    @Override
    public void deleteStaffPersonel(String staffId) {
        staffServiceClient.deleteStaffPersonel(staffId);
    }
}


