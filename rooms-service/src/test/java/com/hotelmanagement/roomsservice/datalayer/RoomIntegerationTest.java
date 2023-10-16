package com.hotelmanagement.roomsservice.datalayer;

import com.hotelmanagement.roomsservice.utils.GlobalControllerExceptionHandler;
import com.hotelmanagement.roomsservice.utils.HttpErrorInfo;
import com.hotelmanagement.roomsservice.utils.exceptions.DuplicateRoomNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class RoomIntegerationTest {
    private  Room preSavedRoom;
    @Autowired
    RoomRepository roomRepository;

    @BeforeEach
    public void setup(){
        roomRepository.deleteAll();
        preSavedRoom = roomRepository.save(new Room("Twin", 250, 232, 100));
    }



    @Test
    public void addNewRoom_shouldWork(){
        // arrange
        String expectedRoomType = "Double";
        Integer expectedRoomSize= 100;
        Integer expectedRoomNumber= 197;
        Integer expectedPrice=100;


        Room newRoom = new  Room(expectedRoomType, expectedRoomSize , expectedRoomNumber, expectedPrice);

        //act
        Room savedRoom = roomRepository.save(newRoom);

        //assert
        assertNotNull(savedRoom);
        assertNotNull(savedRoom.getId());
        assertNotNull(savedRoom.getRoomIdentifier().getRoomId());
        assertEquals(expectedRoomType, savedRoom.getRoomType());
        assertEquals(expectedRoomSize, savedRoom.getRoomSize());
        assertEquals(expectedRoomNumber, savedRoom.getRoomNumber());
        assertEquals(expectedPrice, savedRoom.getPrice());
    }



    @Test
    public void updateRoom_shouldsucced(){
        // arrange
        String updatedRoomType = "Double";
        Integer updatedRoomSize= 100;
        Integer updatedRoomNumber= 990;
        Integer updatedPrice=100;
        preSavedRoom.setRoomType(updatedRoomType);
        preSavedRoom.setRoomNumber(updatedRoomNumber);

        preSavedRoom.setRoomSize(updatedRoomSize);
        preSavedRoom.setPrice(updatedPrice);

        //act
        Room savedRoom= roomRepository.save(preSavedRoom);


        // assert
        assertNotNull(savedRoom);
        assertThat(savedRoom, samePropertyValuesAs(preSavedRoom));

    }

    @Test
    public void findByInvalidRoomNumber_Failed() {
        // Add a new room to the database
        Room newRoom = new Room();


        // Try to find the room by its number
        Room found = roomRepository.findRoomByRoomNumber(preSavedRoom.getRoomNumber()+ 1);

        // Assert that the room was not found
        assertNull(found);


        roomRepository.delete(newRoom);
    }



@Test
public void findByRoomNumber_Success(){

    //act
    Room found = roomRepository.findRoomByRoomNumber(preSavedRoom.getRoomNumber());

    //assert
    assertNotNull(found);
    assertEquals(preSavedRoom.getRoomType(), found.getRoomType());
    assertEquals(preSavedRoom.getRoomSize(), found.getRoomSize());
    //assertEquals(preSavedRoom.getRoomNumber(), found.getRoomNumber());
    assertEquals(preSavedRoom.getPrice(), found.getPrice());

}




    @Test
    public void findByRoomIdentifier_RoomIdSuceed() {
        // Arrange
        Room expected = preSavedRoom;

        // Act
        Room found = roomRepository.findRoomByRoomNumber(expected.getRoomNumber());

        // Assert
        assertNotNull(found);
        assertThat(found, samePropertyValuesAs(expected));
    }


    @Test
    public void findByFalseRoomIdentifier_RoomId_ShouldFail(){
        Room found = roomRepository.findRoomByRoomNumber(
                preSavedRoom.getRoomNumber() +1);

        // assert
        assertNull(found);

    }


}