package com.hotelmanagement.guestsservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GuestRepository extends JpaRepository <Guest, Integer>{
        Guest findByGuestIdentifier_GuestId(String guestId);

        //  findByGuestIdentifier_GuestIdAndEmailAddresss
        // findByGuestIdentifer_GuestIdAndFirstName
        Guest findAllByGuestIdentifier_GuestIdAndEmailAddress(String guestId, String emailAddress);


    List<Guest> findGuestByEmailAddress(String emailAddress);
}
