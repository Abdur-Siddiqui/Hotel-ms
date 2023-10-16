package com.hotelmanagement.staffservice.presentationlayer;


import com.hotelmanagement.staffservice.businesslayer.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping()
    public List<StaffResponseModel> getStaff(){
        return staffService.getStaff();
    }
    @GetMapping("/{staffId}")
    public ResponseEntity<StaffResponseModel> getStaffByStaffId(@PathVariable String staffId){
        return ResponseEntity.ok().body(staffService.getStaffByStaffId(staffId));
    }

    @PostMapping()
    ResponseEntity<StaffResponseModel> addStaff(@RequestBody StaffRequestModel staffRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.addStaff(staffRequestModel));
    }

    @PutMapping("/{staffId}")
    ResponseEntity <StaffResponseModel>updateStaff(@RequestBody StaffRequestModel staffRequestModel, @PathVariable String staffId){
        return ResponseEntity.ok().body(staffService.updateStaff(staffRequestModel, staffId));
    }

    @DeleteMapping("/{staffId}")
    public ResponseEntity<Void> removeStaff(@PathVariable String staffId){
        staffService.removeStaff(staffId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
