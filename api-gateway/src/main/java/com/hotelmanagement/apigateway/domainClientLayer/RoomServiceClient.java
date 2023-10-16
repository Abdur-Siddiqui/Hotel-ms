package com.hotelmanagement.apigateway.domainClientLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagement.apigateway.Utils.Exceptions.InvalidInputException;
import com.hotelmanagement.apigateway.Utils.Exceptions.NotFoundException;
import com.hotelmanagement.apigateway.Utils.HttpErrorInfo;
import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsResponseModel;
import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsRequestModel;
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
public class RoomServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String ROOM_SERVICE_BASE_URI;

    public RoomServiceClient(RestTemplate restTemplate,
                              ObjectMapper objectMapper,
                              @Value("${app.rooms-service.host}") String roomServiceHost,
                              @Value("${app.rooms-service.port}") String roomServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.ROOM_SERVICE_BASE_URI = "http://" + roomServiceHost+ ":" + roomServicePort + "/api/v1/rooms";
    }

    public RoomsResponseModel getRoomByRoomNumber(Integer roomNumber) {
        RoomsResponseModel roomsResponseModel;
        try {
            String url = ROOM_SERVICE_BASE_URI + "/" + roomNumber;
            roomsResponseModel = restTemplate
                    .getForObject(url, RoomsResponseModel.class);

            log.debug("5. Received in API-Gateway Client Service Client getClientAggregate with clientResponseModel : " + roomsResponseModel.getRoomNumber());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return roomsResponseModel;
    }

    public RoomsResponseModel[] getRooms(){
        try {

            RoomsResponseModel[] roomsResponseModels = restTemplate
                    .getForObject(ROOM_SERVICE_BASE_URI, RoomsResponseModel[].class);



            return roomsResponseModels;
        }
        catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public RoomsResponseModel addRoom(RoomsRequestModel roomsRequestModel){
        try {
            RoomsResponseModel roomsResponseModel = restTemplate
                    .postForObject(ROOM_SERVICE_BASE_URI, roomsRequestModel, RoomsResponseModel.class);

            return roomsResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void updateRoom(RoomsRequestModel roomsRequestModel,Integer roomNumber){
        try {
            String url = ROOM_SERVICE_BASE_URI + "/" + roomNumber;
            restTemplate.put(url, roomsRequestModel);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    public void deleteRoom(Integer roomNumber){
        try {
            String url = ROOM_SERVICE_BASE_URI + "/" + roomNumber;
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




