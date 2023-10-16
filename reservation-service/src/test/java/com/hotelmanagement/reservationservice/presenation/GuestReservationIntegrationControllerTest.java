package com.hotelmanagement.reservationservice.presenation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagement.reservationservice.Buissnesslayer.ReservationService;
import com.hotelmanagement.reservationservice.DataLayer.Payment;
import com.hotelmanagement.reservationservice.DataLayer.Status;
import com.hotelmanagement.reservationservice.DomainClientLayer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GuestReservationIntegrationControllerTest {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    RestTemplate restTemplate;
    
    @Mock
    GuestServiceClient guestServiceClient;
    
    @Mock
    StaffServiceClient staffServiceClient;
    
    @Mock
    RoomServiceClient roomServiceClient;
    private MockRestServiceServer mockRestServiceServer;
    private ObjectMapper mapper = new ObjectMapper();
    
    

    @BeforeEach
    public void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenFieldsAreValid_thenReturnPurchaseOrderResponseModel() throws URISyntaxException, JsonProcessingException {
        //arrange
        ReservationRequestModel reservationRequestModel = ReservationRequestModel.builder()
                .roomNumber(256)
                .staffId("c3540a89-cb47-4c96-888e-ff96708db4d8")
                .roomStatus(Status.BOOKED)
                .price(100)
                .dateReservationmade(LocalDate.of(2023, 04, 10))
                .build();

        String guestId = "dd1ab8b0-ab17-4e03-b70a-84caa3871606";
        GuestResponseModel guestResponseModel = new GuestResponseModel(guestId, "Alick", "Ucceli",
                "aucceli0@dot.gov", "73 Shoshone Road", "Barraute", "Québec", "Canada", "P0M 2T6", Payment.Cash);

        //required for get request in employeeServiceClient
        StaffResponseModel staffResponseModel = new StaffResponseModel("c3540a89-cb47-4c96-888e-ff96708db4d8", "Dorry",
                "Dorry", "dgepp1@stanford.edu", "964-472-9806", "Cook", "2023-03-01",
                "Toronto", "J4x 13x");


        RoomsResponseModel roomsResponseModel = new RoomsResponseModel(
                "Single",
                450,
                256,
                100);


        //CLIENT
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7001/api/v1/guests/dd1ab8b0-ab17-4e03-b70a-84caa3871606")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(guestResponseModel)));
        //EMPLOYEE
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(
                        new URI("http://localhost:7003/api/v1/staff/c3540a89-cb47-4c96-888e-ff96708db4d8")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(staffResponseModel)));

        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(
                        new URI("http://localhost:7002/api/v1/rooms/256")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(roomsResponseModel)));


        //act and assert
        String url = "api/v1/guests/" + guestId + "/reservations";
        webTestClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reservationRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ReservationResponseModel.class)
                .value((reservationResponseModel) -> {
                    assertNotNull(reservationResponseModel);
                    assertNotNull(reservationResponseModel.getReservationId());
                    assertEquals(reservationRequestModel.getRoomNumber(), reservationResponseModel.getRoomNumber());
                    assertEquals(guestId, reservationResponseModel.getGuestId());
                    assertEquals(reservationRequestModel.getStaffId(), reservationResponseModel.getStaffId());
                    assertEquals(staffResponseModel.getEmployeeLastName(), reservationResponseModel.getEmployeeFirstName());
                    assertEquals(staffResponseModel.getEmployeeLastName(), reservationResponseModel.getEmployeeLastName());
                    assertEquals(guestResponseModel.getFirstName(), reservationResponseModel.getGuestFirstName());
                    assertEquals(guestResponseModel.getLastName(), reservationResponseModel.getGuestLastName());
                    assertEquals(reservationRequestModel.getPrice(), reservationResponseModel.getPrice());
                    assertEquals(reservationRequestModel.getRoomStatus(), reservationResponseModel.getRoomStatus());
                    assertEquals(reservationRequestModel.getDateReservationmade(), reservationResponseModel.getReservationDateMade());

                });

    }
    // this code didnt run
/*
        @Test
        public void whenguestId_GetPurchases() throws URISyntaxException, JsonProcessingException{
            ReservationRequestModel reservationRequestModel = ReservationRequestModel.builder()
                    .staffId("d846a5a7-2e1c-4c79-809c-4f3f471e826d")
                    .roomNumber(256)
                    .roomStatus(Status.BOOKED)
                    .price(100)
                    .dateReservationmade(LocalDate.of(2023, 04, 10))
                    .build();

            String guestId = "dd1ab8b0-ab17-4e03-b70a-84caa3871606";
            GuestResponseModel guestResponseModel = new GuestResponseModel(guestId, "Alick", "Ucceli",
                    "aucceli0@dot.gov", "73 Shoshone Road", "Barraute", "Québec", "Canada", "P0M 2T6", Payment.Cash);

            //required for get request in employeeServiceClient
            StaffResponseModel staffResponseModel = new StaffResponseModel("c3540a89-cb47-4c96-888e-ff96708db4d8", "Dorry",
                    "Dorry", "dgepp1@stanford.edu", "964-472-9806", "Cook", "2023-03-01",
                    "Toronto", "J4x 13x");


            RoomsResponseModel roomsResponseModel = new RoomsResponseModel(
                    "Single",
                    450,
                    256,
                    100);


            mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7001/api/v1/guests/dd1ab8b0-ab17-4e03-b70a-84caa3871606")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(mapper.writeValueAsString(guestResponseModel)));
            mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7002/api/v1/rooms/256")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(mapper.writeValueAsString(roomsResponseModel)));
            mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7003/api/v1/staff/c3540a89-cb47-4c96-888e-ff96708db4d8")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(mapper.writeValueAsString(staffResponseModel)));

            when(guestServiceClient.getGuestByGuestId(guestId)).thenReturn(guestResponseModel);
            when(staffServiceClient.getStaffById(reservationRequestModel.getStaffId())).thenReturn(staffResponseModel);
            when(roomServiceClient.getRoomByRoomNumber(reservationRequestModel.getRoomNumber())).thenReturn(roomsResponseModel);
            String url = "api/v1/guests/"+guestId+"/reservations";
            Integer ActualNumPurchases = 0;
            webTestClient.get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.length()").isEqualTo(ActualNumPurchases);

        }


*/


}

