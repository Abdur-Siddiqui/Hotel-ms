package com.hotelmanagement.apigateway.presentationLayer.Staff;


import com.hotelmanagement.apigateway.Utils.Exceptions.InvalidInputException;
import com.hotelmanagement.apigateway.businessLayer.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/staff")
public class StaffController {

    private  StaffService staffService;

    private final Integer UUID_SIZE = 36;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }



    @GetMapping(value = "/{staffId}", produces = "application/json")
    ResponseEntity<StaffResponseModel> getStaffById(@PathVariable String staffId){
        if(staffId.length() != UUID_SIZE){
            throw new InvalidInputException("StaffId is invalid: " + staffId);
        }

        log.debug("1, Received in API_Gateway staff Controller getstaffbystaffid with staffId");
        return ResponseEntity.ok().body(staffService.getStaffById(staffId));
    }

    @GetMapping(
            produces = "application/json"
    )
    ResponseEntity<StaffResponseModel[]> getStaffMembers(){
        log.debug("1, Received in API_Gateway staff Controller getStaff");
        return ResponseEntity.ok().body(staffService.getStaffMembers());
    }

    @PostMapping(
            produces = "application/json"
    )
    ResponseEntity<StaffResponseModel> addStaff(@RequestBody StaffRequestModel staffRequestModel){
        log.debug("1, Received in API_Gateway Staff Controller addStaff");
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.addStaff(staffRequestModel));
    }

    @PutMapping("/{staffId}")
    void updateStaffPersonel(@RequestBody StaffRequestModel staffRequestModel, @PathVariable String staffId){
        staffService.updateStaffPersonel(staffRequestModel, staffId);
    }

    @DeleteMapping("/{staffId}")
    void deleteStaffPersonel(@PathVariable String staffId){
      staffService.deleteStaffPersonel(staffId);
    }

}

