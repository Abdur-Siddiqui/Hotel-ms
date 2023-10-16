package com.hotelmanagement.apigateway.domainClientLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagement.apigateway.Utils.Exceptions.InvalidInputException;
import com.hotelmanagement.apigateway.Utils.Exceptions.NotFoundException;
import com.hotelmanagement.apigateway.Utils.HttpErrorInfo;
import com.hotelmanagement.apigateway.presentationLayer.Guests.GuestRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Guests.GuestResponseModel;
import com.hotelmanagement.apigateway.presentationLayer.Reservation.ReservationRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Reservation.ReservationResponseModel;
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
public class ReservationServiceCleint  {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String RESERVATIONS_SERVICE_BASE_URI;

    public ReservationServiceCleint(RestTemplate restTemplate,
                              ObjectMapper objectMapper,
                              @Value("${app.reservation-service.host}") String reservationServiceHost,
                              @Value("${app.reservation-service.port}") String reservationServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.RESERVATIONS_SERVICE_BASE_URI = "http://" + reservationServiceHost+ ":" + reservationServicePort + "/api/v1/guests/{guestId}/reservations";
    }

    public ReservationResponseModel getGuestReservationByReservationId(String guestId, String reservationId) {
        try {
            String url = "/api/v1/guests/" + guestId+"/reservations/"+reservationId;
            ReservationResponseModel reservationResponseModel = restTemplate.getForObject(url, ReservationResponseModel.class);
            return reservationResponseModel;
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }

    }

    public ReservationResponseModel[] getAllReservations(){
        try {

            ReservationResponseModel[] reservationResponseModels = restTemplate
                    .getForObject(RESERVATIONS_SERVICE_BASE_URI, ReservationResponseModel[].class);



            return reservationResponseModels;
        }
        catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public ReservationResponseModel addGuestsReservation(ReservationRequestModel reservationRequestModel){
        try {
             ReservationResponseModel reservationResponseModel = restTemplate
                    .postForObject(RESERVATIONS_SERVICE_BASE_URI, reservationRequestModel, ReservationResponseModel.class);
             return reservationResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void updateGuestReservation(ReservationRequestModel reservationRequestModel, String guestId, String reservationId){
        try {
            String url = "/api/v1/guests/" + guestId+"/ reservations/"+ reservationId;
            restTemplate.put(url, reservationRequestModel);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    public void delete(String guestId, String reservationId){
        try {
            String url = "/api/v1/guests/" + guestId+"/reservations/"+ reservationId;
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



