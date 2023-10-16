package com.hotelmanagement.guestsservice.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class GuestPersistanceTest {
    private Guest presavedGuest;

    @Autowired
    GuestRepository guestRepository;


    @BeforeEach
    public void setupTestDb() {
        guestRepository.deleteAll();
        presavedGuest = guestRepository.save(
                new Guest(
                        "jon",
                        "doe",
                        "elias67@gmail.com",
                        new Address( "123 rue mcodnalds", " Montreal", "Quebec" , "Canada", "j7x 1h2")
                ));
    }
    @Test
    public void savestaff_shouldsucced(){
        String expectedStaffName = "jon";
        Guest newSTAFF =  new Guest(
                "jon",
                "doe",
                "tlias67@gmail.com",
                new Address( "123 rue mcodnalds", " Montreal", "Quebec" , "Canada", "j7x 1h2")
        );

        Guest newSavedStaff = guestRepository.save(newSTAFF);

        assertNotNull(newSavedStaff);
        assertNotNull(newSavedStaff.getId());
        assertNotNull(newSavedStaff.getGuestIdentifier().getGuestId());
        assertEquals(expectedStaffName, newSavedStaff.getFirstName());
    }

    @Test
    public void addNewStaff_shouldWork(){
        // arrange
        String expectedStaffName = "jon";


        Guest newStaff =   new Guest(
                "jon",
                "doe",
                "hlias67@gmail.com",
                new Address( "123 rue mcodnalds", " Montreal", "Quebec" , "Canada", "j7x 1h2")
        );

        //act
        Guest savedStaff = guestRepository.save(newStaff);

        //assert
        assertNotNull(savedStaff);
        assertNotNull(savedStaff.getId());
        assertNotNull(savedStaff.getGuestIdentifier().getGuestId());
        assertEquals(expectedStaffName, savedStaff.getFirstName());
    }



    @Test
    public void updateStaff_shouldSucceed() {

        //arrange
        String expectedStafflastName = "Elamine";
        presavedGuest.setLastName(expectedStafflastName);

        //act
        Guest savedStaff = guestRepository.save(presavedGuest);

        //assert
        assertNotNull(savedStaff);
        assertThat(savedStaff, samePropertyValuesAs(presavedGuest));
    }


    @Test
    public void findStaffByStaffIdentifier_StaffId_Successful() {

        //act
        Guest found = guestRepository.findByGuestIdentifier_GuestId(presavedGuest.getGuestIdentifier().getGuestId());

        //assert
        assertNotNull(found);
        assertThat(presavedGuest, samePropertyValuesAs(found));

    }

    @Test
    public void findStaffByStaffIdentifier_StaffId_Failed() {

        //act
        Guest found = guestRepository.findByGuestIdentifier_GuestId(presavedGuest.getGuestIdentifier().getGuestId() + 1);

        //assert
        assertNull(found);

    }




}