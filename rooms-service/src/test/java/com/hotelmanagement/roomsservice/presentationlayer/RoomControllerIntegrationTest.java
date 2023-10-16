package com.hotelmanagement.roomsservice.presentationlayer;

import com.hotelmanagement.roomsservice.datalayer.Room;
import com.hotelmanagement.roomsservice.datalayer.RoomIdentifier;
import com.hotelmanagement.roomsservice.datalayer.RoomRepository;
import com.hotelmanagement.roomsservice.utils.GlobalControllerExceptionHandler;
import com.hotelmanagement.roomsservice.utils.HttpErrorInfo;
import com.hotelmanagement.roomsservice.utils.exceptions.DuplicateRoomNumberException;
import com.hotelmanagement.roomsservice.utils.exceptions.NotFoundException;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment= RANDOM_PORT)
@Sql({"/data-mysql.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoomControllerIntegrationTest {


    private final String BASE_URI_ROOM = "/api/v1/rooms";
    //private final String VALID_ROOM_ID = "30a4e2e3-fe9d-4903-bd23-b0a72b6c4ced";

    private final Integer  VALID_ROOM_NUMBER= 256;

    private final Integer DUPLICATE_ROOM_NUMBER= VALID_ROOM_NUMBER;
    private final Integer UNKNOWN_ROOM_NUMBER = VALID_ROOM_NUMBER + 1;
    private final String VALID_ROOM_TYPE = "Suite";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    RoomRepository roomRepository;


    // getting all the rooms
    @Test
    public void whenRoomsExist_thenReturnAllRooms() {
        // arrange
        Integer expectedNumRooms = 2;

        // act
        webTestClient.get()
                .uri(BASE_URI_ROOM)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(expectedNumRooms);

    }

    // getting rooms by id
    @Test
    public void whenGetRoomWithValidIdExists_thenReturnRoom() {
        webTestClient.get().uri(BASE_URI_ROOM + "/" + VALID_ROOM_NUMBER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.roomNumber").isEqualTo(VALID_ROOM_NUMBER)
                .jsonPath("$.roomType").isEqualTo(VALID_ROOM_TYPE);
    }


    // creating a post
    @Test
    public void whenCreateRoomWithValidValues_thenReturnNewRoom() {

        //arrange
        String roomType = "Twin";
        Integer roomSize = 340;
        Integer roomNumber = 76;
        Integer price = 180;


        RoomsRequestModel roomsRequestModel = new RoomsRequestModel( roomNumber, roomType, roomSize, price);

        //act and assert
        webTestClient.post()
                .uri(BASE_URI_ROOM)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roomsRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.roomNumber").isNotEmpty()
                .jsonPath("$.roomType").isEqualTo(roomType)
                .jsonPath("$.roomSize").isEqualTo(roomSize)
                .jsonPath("$.price").isEqualTo(price);

    }


    @Test
    public void whenUpdateRoomWithValidValues_thenReturnUpdatedRoom() {

        //arrange
        String roomType = "Twin";
        Integer roomSize = 340;
        Integer roomNumber = 226;
        Integer price = 290;

        RoomsRequestModel roomsRequestModel = new RoomsRequestModel( roomNumber, roomType, roomSize, price);

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_ROOM + "/" + VALID_ROOM_NUMBER)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roomsRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.roomId").isNotEmpty()
                .jsonPath("$.roomType").isEqualTo(roomType)
                .jsonPath("$.roomSize").isEqualTo(roomSize)
                .jsonPath("$.roomNumber").isEqualTo(roomNumber)
                .jsonPath("$.price").isEqualTo(price);


    }

    @Test
    public void whenUpdateRoomWithUnknownRoomId_thenThrowNotFoundException() {

        //arrange
        String roomType = "Twin";
        Integer roomSize = 340;
        Integer roomNumber = 112;
        Integer price = 230;

        RoomsRequestModel roomsRequestModel = new RoomsRequestModel( roomNumber, roomType, roomSize, price);



        webTestClient.put()
                .uri(BASE_URI_ROOM + "/" + UNKNOWN_ROOM_NUMBER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roomsRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isEqualTo(HttpStatusCode.valueOf(404))
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.path")
                .isEqualTo("uri=" + BASE_URI_ROOM + "/" + UNKNOWN_ROOM_NUMBER)
                .jsonPath("$.message").isEqualTo("Unknown room number: " + UNKNOWN_ROOM_NUMBER);






       /* WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        NotFoundException exception = new NotFoundException("Not Found");
        GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();
        HttpErrorInfo errorInfo = handler.handleNotFoundException(request, exception);
        assertNotNull(errorInfo);
        assertEquals(HttpStatus.NOT_FOUND, errorInfo.getHttpStatus());
        assertEquals("Not Found", errorInfo.getMessage());
        assertEquals(request.getDescription(false), errorInfo.getPath());*/
    }

/*
    // testing specific exception
    @Test
    public void whenUpdateRoomAndDuplicate_throw_RoomNumberException() {
        String roomType = "Twin";
        Integer roomSize = 340;
        Integer roomNumber = 256;
        Integer price = 230;

        RoomsRequestModel roomsRequestModel = new RoomsRequestModel( roomNumber, roomType, roomSize, price);

        webTestClient.put()
                .uri(BASE_URI_ROOM + "/" + DUPLICATE_ROOM_NUMBER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roomsRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isEqualTo(HttpStatusCode.valueOf(422))
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.path")
                .isEqualTo("uri=" + BASE_URI_ROOM + "/" + DUPLICATE_ROOM_NUMBER)
                .jsonPath("$.message").isEqualTo("Room already contains a room with this room number: " + DUPLICATE_ROOM_NUMBER);




       /* WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        DuplicateRoomNumberException exception = new DuplicateRoomNumberException("Duplicate Room Number");
        GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();
        HttpErrorInfo errorInfo = handler.handleDuplicateRoomNumberException(request, exception);
        assertNotNull(errorInfo);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, errorInfo.getHttpStatus());
        assertEquals("Duplicate Room Number", errorInfo.getMessage());
        assertEquals(request.getDescription(false), errorInfo.getPath());

    }
*/

    @Test
    public void whenUpdateRoomAndDuplicate_throw_RoomNumberException() {
        String roomType = "Twin";
        Integer roomSize = 340;
        Integer newRoomNumber = DUPLICATE_ROOM_NUMBER;
        Integer price = 230;
        Integer roomToBeUpdated = 112;

        RoomsRequestModel roomsRequestModel = new RoomsRequestModel(newRoomNumber, roomType, roomSize, price);

        webTestClient.put()
                .uri(BASE_URI_ROOM + "/" + roomToBeUpdated).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roomsRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isEqualTo(HttpStatusCode.valueOf(422))
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI_ROOM + "/" + roomToBeUpdated)
                .jsonPath("$.message").isEqualTo("Room already contains a room with this room number: " + newRoomNumber);
    }

    @Test
    public void whenCreateRoomWithDuplicateValues_thenReturnRoomNumberException() {
        // arrange
        String roomType = "Twin";
        Integer roomSize = 340;
        Integer price = 180;
        Integer roomNumber = 256;
        RoomsRequestModel roomsRequestModel = new RoomsRequestModel( roomNumber, roomType, roomSize, price);

// act and assert
        webTestClient.post()
                .uri(BASE_URI_ROOM)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roomsRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(422))
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.path")
                .isEqualTo("uri=" + BASE_URI_ROOM)
                .jsonPath("$.message").isEqualTo("Room already contains a room with this room number: " + DUPLICATE_ROOM_NUMBER);


    }


    @Test
    public void whenGetRoomWithUnknownRoomId_thenThrowNotFoundException() {
        webTestClient.get()
                .uri(BASE_URI_ROOM + "/" + UNKNOWN_ROOM_NUMBER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI_ROOM + "/" + UNKNOWN_ROOM_NUMBER)
                .jsonPath("$.message").isEqualTo("Unknown room number : " + UNKNOWN_ROOM_NUMBER);
    }

       /* WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        NotFoundException exception = new NotFoundException("Not Found");
        GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();
        HttpErrorInfo errorInfo = handler.handleNotFoundException(request, exception);
        assertNotNull(errorInfo);
        assertEquals(HttpStatus.NOT_FOUND, errorInfo.getHttpStatus());
        assertEquals("Not Found", errorInfo.getMessage());
        assertEquals(request.getDescription(false), errorInfo.getPath());
            }
 */


    @Test
    public void whenDeleteRoom_thenDeleteRoom() {

        webTestClient.delete()
                .uri(BASE_URI_ROOM + "/" + VALID_ROOM_NUMBER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();

    }


    @Test
    public void whenDeleteRoomWithUnknownRoomId_thenThrowNotFoundException() {



        // Act and Assert
        webTestClient.delete()
                .uri(BASE_URI_ROOM + "/" + UNKNOWN_ROOM_NUMBER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI_ROOM + "/" + UNKNOWN_ROOM_NUMBER)
                .jsonPath("$.message").isEqualTo("Unknown room number provided:" + UNKNOWN_ROOM_NUMBER);

    }



}