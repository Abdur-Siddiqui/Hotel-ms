package com.hotelmanagement.apigateway.businessLayer;

import com.hotelmanagement.apigateway.domainClientLayer.GuestServiceClient;
import com.hotelmanagement.apigateway.presentationLayer.Guests.GuestRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Guests.GuestResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GuestServiceImpl implements GuestsService {
    private GuestServiceClient guestServiceClient;

    public GuestServiceImpl(GuestServiceClient guestServiceClient) {
        this.guestServiceClient = guestServiceClient;
    }

    @Override
    public GuestResponseModel getGuestByGuestId(String guestId) {
        return guestServiceClient.getGuestByGuestId(guestId);
    }

    @Override
    public GuestResponseModel[] getGuests() {
        log.debug("2. Received in API-Gateway Guest Service Impl getGuests");
        return guestServiceClient.getGuests();
    }

    @Override
    public GuestResponseModel addGuest(GuestRequestModel guestRequestModel) {

            log.debug("2. Received in API-Gateway Inventory Service Impl addInventory");
            return guestServiceClient.addGuest(guestRequestModel);
        }


        @Override
    public void updateGuest(GuestRequestModel guestRequestModel, String guestId) {
            guestServiceClient.updateGuest(guestRequestModel, guestId);
    }

    @Override
    public void deleteGuest(String guestId) {
     guestServiceClient.deleteGuest(guestId);
    }
}
