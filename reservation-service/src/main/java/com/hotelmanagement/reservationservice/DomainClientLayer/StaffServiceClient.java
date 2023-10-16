package com.hotelmanagement.reservationservice.DomainClientLayer;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.hotelmanagement.reservationservice.utils.HttpErrorInfo;
import com.hotelmanagement.reservationservice.utils.exeption.InvalidInputException;
import com.hotelmanagement.reservationservice.utils.exeption.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@Component
public class StaffServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String STAFF_SERVICE_BASE_URI;

    public StaffServiceClient(RestTemplate restTemplate,
                              ObjectMapper objectMapper,
                              @Value("${app.staff-service.host}") String staffServiceHost,
                              @Value("${app.staff-service.port}") String staffServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.STAFF_SERVICE_BASE_URI = "http://" + staffServiceHost+ ":" + staffServicePort + "/api/v1/staff";
    }

    public StaffResponseModel getStaffById(String staffId) {
        StaffResponseModel staffResponseModel;
        try {
            String url = STAFF_SERVICE_BASE_URI + "/" + staffId;
            staffResponseModel = restTemplate
                    .getForObject(url, StaffResponseModel.class);

            log.debug("5. Received in API-Gateway Client Service Client getClientAggregate with clientResponseModel : " + staffResponseModel.getStaffId());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return staffResponseModel;
    }

    public StaffResponseModel[] getStaffMembers(){
        try {
            //List<VehicleStaffResponseModel> vehicleStaffResponseModelList = new ArrayList<>();
            StaffResponseModel[] staffResponseModels = restTemplate
                    .getForObject(STAFF_SERVICE_BASE_URI, StaffResponseModel[].class);

           /* for (VehicleStaffResponseModel vehicleStaffResponseModel:
                 vehicleStaffResponseModels) {
                vehicleStaffResponseModelList.add(vehicleStaffResponseModel);
            }*/

            return staffResponseModels;
        }
        catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public StaffResponseModel addStaff(StaffRequestModel staffRequestModel){
        try {
            StaffResponseModel staffResponseModel = restTemplate
                    .postForObject(STAFF_SERVICE_BASE_URI, staffRequestModel, StaffResponseModel.class);

            return staffResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void updateStaffPersonel(StaffRequestModel staffRequestModel, String staffId){
        try {
            String url = STAFF_SERVICE_BASE_URI + "/" + staffId;
            restTemplate.put(url, staffRequestModel);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    public void deleteStaffPersonel(String staffId){
        try {
            String url = STAFF_SERVICE_BASE_URI + "/" + staffId;
            restTemplate.delete(url);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }


    }
}


