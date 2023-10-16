package com.hotelmanagement.staffservice.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
@DataJpaTest
public class StaffPersonalPersistanceTest {
    private StaffPersonal preSavedStaff;

    @Autowired
    StaffRepository staffRepository;


    @BeforeEach
    public void setupTestDb() {
        staffRepository.deleteAll();
        preSavedStaff = staffRepository.save(new StaffPersonal("jon", "doe", "elias67@gmail.com", "998-111-990", " Cook", "2023-05-18", new Address( " Montreal", "j7x 1h2") ));
    }
    @Test
    public void savestaff_shouldsucced(){
        String expectedStaffName = "Science";
        StaffPersonal newSTAFF = new StaffPersonal(expectedStaffName,"doe", "elias67@gmail.com", "998-198-990", " Cook", "2023-05-18", new Address(" Montreal",  "j7x 1h2"));

        StaffPersonal newSavedStaff = staffRepository.save(newSTAFF);

        assertNotNull(newSavedStaff);
        assertNotNull(newSavedStaff.getId());
       assertNotNull(newSavedStaff.getStaffPersonalIdentifier().getStaffId());
       assertEquals(expectedStaffName, newSavedStaff.getEmployeeFirstName());
    }

    @Test
    public void addNewStaff_shouldWork(){
        // arrange
        String expectedStaffName = "Science";


        StaffPersonal newStaff = new StaffPersonal(expectedStaffName,"doe", "elias67@gmail.com", "998-198-990", " Cook", "2023-05-18", new Address(" Montreal",  "j7x 1h2"));

        //act
        StaffPersonal savedStaff = staffRepository.save(newStaff);

        //assert
        assertNotNull(savedStaff);
        assertNotNull(savedStaff.getId());
        assertNotNull(savedStaff.getStaffPersonalIdentifier().getStaffId());
        assertEquals(expectedStaffName, savedStaff.getEmployeeFirstName());
    }



    @Test
    public void updateStaff_shouldSucceed() {

        //arrange
        String expectedStafflastName = "Elamine";
        preSavedStaff.setEmployeeLastName(expectedStafflastName);

        //act
        StaffPersonal savedStaff = staffRepository.save(preSavedStaff);

        //assert
        assertNotNull(savedStaff);
        assertThat(savedStaff, samePropertyValuesAs(preSavedStaff));
    }


    @Test
    public void findStaffByStaffIdentifier_StaffId_Successful() {

        //act
        StaffPersonal found = staffRepository.findByStaffPersonalIdentifier_StaffId(preSavedStaff.getStaffPersonalIdentifier().getStaffId());

        //assert
        assertNotNull(found);
        assertThat(preSavedStaff, samePropertyValuesAs(found));

    }

    @Test
    public void findStaffByStaffIdentifier_StaffId_Failed() {

        //act
        StaffPersonal found = staffRepository.findByStaffPersonalIdentifier_StaffId(preSavedStaff.getStaffPersonalIdentifier().getStaffId() + 1);

        //assert
        assertNull(found);

    }



}