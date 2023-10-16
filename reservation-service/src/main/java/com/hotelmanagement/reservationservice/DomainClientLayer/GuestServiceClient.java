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
public class GuestServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String GUEST_SERVICE_BASE_URI;

    public GuestServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.guests-service.host}") String guestServiceHost,
                               @Value("${app.guests-service.port}") String guestServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.GUEST_SERVICE_BASE_URI = "http://" + guestServiceHost+ ":" + guestServicePort + "/api/v1/guests";
    }

    public GuestResponseModel getGuestByGuestId(String guestId) {
        GuestResponseModel guestResponseModel;
        try {
            String url = GUEST_SERVICE_BASE_URI + "/" + guestId;
            guestResponseModel = restTemplate
                    .getForObject(url, GuestResponseModel.class);

            log.debug("5. Received in API-Gateway Client Service Client getClientAggregate with clientResponseModel : " + guestResponseModel.getGuestId());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return guestResponseModel;
    }

    public GuestResponseModel[] getGuests(){
        try {
            //List<VehicleGuestResponseModel> vehicleGuestResponseModelList = new ArrayList<>();
            GuestResponseModel[] guestResponseModels = restTemplate
                    .getForObject(GUEST_SERVICE_BASE_URI, GuestResponseModel[].class);

           /* for (VehicleGuestResponseModel vehicleGuestResponseModel:
                 vehicleGuestResponseModels) {
                vehicleGuestResponseModelList.add(vehicleGuestResponseModel);
            }*/

            return guestResponseModels;
        }
        catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public GuestResponseModel addGuest(GuestRequestModel guestRequestModel){
        try {
            GuestResponseModel guestResponseModel = restTemplate
                    .postForObject(GUEST_SERVICE_BASE_URI, guestRequestModel, GuestResponseModel.class);

            return guestResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void updateGuest(GuestRequestModel guestRequestModel, String guestId){
        try {
            String url = GUEST_SERVICE_BASE_URI + "/" + guestId;
            restTemplate.put(url, guestRequestModel);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    public void deleteGuest(String guestId){
        try {
            String url = GUEST_SERVICE_BASE_URI + "/" + guestId;
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


