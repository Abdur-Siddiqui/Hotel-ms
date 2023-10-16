package com.hotelmanagement.guestsservice.businesslayer;

import com.hotelmanagement.guestsservice.presentationlayer.GuestRequestModel;
import com.hotelmanagement.guestsservice.presentationlayer.GuestResponseModel;

import java.util.List;

public interface GuestService {

    List<GuestResponseModel> getGuests();
   // List<Guest> getguests();
    GuestResponseModel getGuestByGuestId(String guestId);
    GuestResponseModel updateGuest(GuestRequestModel guestRequestModel, String guestId);
    GuestResponseModel addGuest (GuestRequestModel guestRequestModel);

    void removeGuest(String guestId);
}
