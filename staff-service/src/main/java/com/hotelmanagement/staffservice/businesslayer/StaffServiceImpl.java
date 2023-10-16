package com.hotelmanagement.staffservice.businesslayer;


import com.hotelmanagement.staffservice.datalayer.Address;
import com.hotelmanagement.staffservice.datalayer.StaffPersonal;
import com.hotelmanagement.staffservice.datalayer.StaffRepository;
import com.hotelmanagement.staffservice.datamapperlayer.StaffRequestMapper;
import com.hotelmanagement.staffservice.datamapperlayer.StaffResponseMapper;
import com.hotelmanagement.staffservice.presentationlayer.StaffRequestModel;
import com.hotelmanagement.staffservice.presentationlayer.StaffResponseModel;
import com.hotelmanagement.staffservice.utils.exceptions.DuplicatePhoneNumberException;
import com.hotelmanagement.staffservice.utils.exceptions.InvalidInputException;
import com.hotelmanagement.staffservice.utils.exceptions.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final StaffResponseMapper staffResponseMapper;
    private final StaffRequestMapper staffRequestMapper;

    public StaffServiceImpl(StaffRepository staffRepository, StaffResponseMapper staffResponseMapper, StaffRequestMapper staffRequestMapper) {
        this.staffRepository = staffRepository;
        this.staffResponseMapper = staffResponseMapper;
        this.staffRequestMapper = staffRequestMapper;
    }




    @Override
    public List<StaffResponseModel> getStaff() {
        return staffResponseMapper.entityListToResponseModelList(staffRepository.findAll());
    }

    @Override
    public StaffResponseModel getStaffByStaffId(String staffId) {
        StaffPersonal existingStaff = staffRepository.findByStaffPersonalIdentifier_StaffId((staffId));
        if(existingStaff == null){
            throw new NotFoundException("Unknown Staff ID Provided: " + staffId);
        }
        return staffResponseMapper.entityToResponseModel(staffRepository.findByStaffPersonalIdentifier_StaffId(staffId));
    }

    @Override
    public StaffResponseModel updateStaff(StaffRequestModel staffRequestModel, String staffId) throws NotFoundException, DuplicatePhoneNumberException {

        // checking if staff id is null or not
        StaffPersonal existingStaff = staffRepository.findByStaffPersonalIdentifier_StaffId(staffId);
        if(existingStaff == null){
            throw new NotFoundException("Unknown staff ID Provided: " + staffId);
        }

        Address address = new Address(staffRequestModel.getEmployeeCity(),
                staffRequestModel.getEmployeePostalCode());
        StaffPersonal updatedStaff = staffRequestMapper.requestModelToEntity(staffRequestModel);
        updatedStaff.setAddress(address);

        updatedStaff.setId(existingStaff.getId());
        updatedStaff.setStaffPersonalIdentifier(existingStaff.getStaffPersonalIdentifier());

        String existingPhoneNumber = updatedStaff.getEmployeePhoneNumber();
        List<StaffPersonal> foundStaff = staffRepository.findByEmployeePhoneNumber(existingPhoneNumber);

        if (foundStaff.size() > 0 && !existingStaff.getEmployeePhoneNumber().equals(existingPhoneNumber)) {
            throw new DuplicatePhoneNumberException("Staff already contains a staff with this phone number: " + existingPhoneNumber);
        }

        StaffPersonal savedStaff = staffRepository.save(updatedStaff);
        return staffResponseMapper.entityToResponseModel(savedStaff);
    }




    @Override
    public StaffResponseModel addStaff(StaffRequestModel staffRequestModel) throws DuplicatePhoneNumberException {

        StaffPersonal staffPersonal = staffRequestMapper.requestModelToEntity(staffRequestModel);

        String existingPhoneNumber = staffPersonal.getEmployeePhoneNumber();
        List<StaffPersonal> foundStaff = staffRepository.findByEmployeePhoneNumber(existingPhoneNumber);

        if (foundStaff.size() > 0) {
            throw new DuplicatePhoneNumberException("Staff already contains a staff with this staff number: " +
                    staffRequestModel.getEmployeePhoneNumber() );
        } else if (existingPhoneNumber.length() > 17) {
            throw new InvalidInputException("phone number  is too long: " + existingPhoneNumber);
        }

        else {

            StaffPersonal saved = staffRepository.save(staffPersonal);
            return staffResponseMapper.entityToResponseModel(saved);
        }
    }

    @Override
    public void removeStaff(String staffId) {
        StaffPersonal existingStaff = staffRepository.findByStaffPersonalIdentifier_StaffId(staffId);

        if(existingStaff == null){
            throw new NotFoundException("Unknown Staff ID Provided: " + staffId);
        }

        staffRepository.delete(existingStaff);
    }
    
}





