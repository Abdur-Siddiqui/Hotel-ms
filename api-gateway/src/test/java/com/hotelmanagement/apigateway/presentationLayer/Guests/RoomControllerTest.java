package com.hotelmanagement.apigateway.presentationLayer.Guests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagement.apigateway.businessLayer.RoomsService;
import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsRequestModel;
import com.hotelmanagement.apigateway.presentationLayer.Rooms.RoomsResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RoomControllerTest {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    RestTemplate restTemplate;
    private MockRestServiceServer mockRestServiceServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @MockBean
    private RoomsService roomsService;

    @Test
    public void testGetRoomByRoomNumber() {
        Integer roomNumber = 1;

        webTestClient.get().uri("/api/v1/rooms/{roomNumber}", roomNumber)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RoomsResponseModel.class);
    }

    @Test
    public void testGetRooms() {
        webTestClient.get().uri("/api/v1/rooms")
                .exchange()
                .expectStatus().isOk()
                .expectBody(RoomsResponseModel[].class);
    }

    @Test
    public void testAddRoom() {
        RoomsRequestModel requestModel = new RoomsRequestModel("Suite", 150, 122, 189);
        // Set requestModel properties if needed

        webTestClient.post().uri("/api/v1/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestModel), RoomsRequestModel.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RoomsResponseModel.class);
    }

    @Test
    public void testUpdateRoom() {
        Integer roomNumber = 1;
        RoomsRequestModel requestModel = new RoomsRequestModel("Suite", 150, 122, 189);
        // Set requestModel properties if needed

        webTestClient.put().uri("/api/v1/rooms/{roomNumber}", roomNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestModel), RoomsRequestModel.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testDeleteRoom() {
        Integer roomNumber = 1;

        webTestClient.delete().uri("/api/v1/rooms/{roomNumber}", roomNumber)
                .exchange()
                .expectStatus().isOk();
    }
}

