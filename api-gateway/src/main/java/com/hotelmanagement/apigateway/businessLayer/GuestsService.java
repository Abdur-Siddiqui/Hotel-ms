package com.hotelmanagement.apigateway.businessLayer;

import com.hotelmanagement.apigateway.presentationLayer.Guests.GuestRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Guests.GuestResponseModel;

public interface GuestsService {
 GuestResponseModel getGuestByGuestId(String guestId);
 GuestResponseModel[] getGuests();
 GuestResponseModel addGuest(GuestRequestModel guestRequestModel);
 void updateGuest(GuestRequestModel guestRequestModel, String guestId);
 void deleteGuest(String guestId);
}
