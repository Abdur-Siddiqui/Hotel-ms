package com.hotelmanagement.staffservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<StaffPersonal, Integer> {


    StaffPersonal findByStaffPersonalIdentifier_StaffId(String staffId);
    List<StaffPersonal> findByEmployeePhoneNumber(String phoneNumber) ;



}
